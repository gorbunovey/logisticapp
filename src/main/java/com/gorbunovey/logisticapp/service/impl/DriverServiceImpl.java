package com.gorbunovey.logisticapp.service.impl;

import com.gorbunovey.logisticapp.dao.api.CityDAO;
import com.gorbunovey.logisticapp.dao.api.DriverDAO;
import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.UserEntity;
import com.gorbunovey.logisticapp.service.api.DriverService;
import com.gorbunovey.logisticapp.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDAO driverDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public void addDriver(DriverDTO driverDTO) {
        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setNumber(driverDTO.getNumber());
        driverEntity.setStatus(DriverDTO.statuses[3]);
        driverEntity.setHours(0);
        driverEntity.setOnShift(false);
        driverEntity.setLastShiftTime(LocalDateTime.now());
        driverEntity.setCity(cityDAO.getByCode(driverDTO.getCityCode())); // set City
        UserEntity userEntity = userService.getUserEntity(driverDTO.getUserId()); // get chosen User
        userService.updateUserRole(driverDTO.getUserId(), "Driver"); // set new Role
        driverEntity.setUser(userEntity); // set new User
        driverDAO.add(driverEntity);
    }

    @Override
    public DriverDTO getDriverByNumber(Long number) {
        DriverEntity driverEntity = driverDAO.getByNumber(number);
        if (driverEntity == null) {
            return null;
        } else {
            return modelMapper.map(driverEntity, DriverDTO.class);
        }
    }

    @Override
    @Transactional
    public boolean updateDriver(DriverDTO driverDTO) {
        // Получаю сущность:
        DriverEntity driverEntity = driverDAO.getByNumber(driverDTO.getOldNumber()); // get old Driver
        if (driverEntity == null) {
            return false;
        } else {
            // обновляю простые поля:
            driverEntity.setNumber(driverDTO.getNumber()); // set new Driver's own number
            // Должен ли менеджер иметь право менять эти поля???
//            driverEntity.setStatus(driverDTO.getStatus());
//            driverEntity.setHours(driverDTO.getHours());
//            driverEntity.setOnShift(driverDTO.isOnShift());
            driverEntity.setCity(cityDAO.getByCode(driverDTO.getCityCode())); // set new City
            // обновляю поля, которые требуют каскадных изменений, не доступных с этой стороны:
            // т.е. обновляю роль у старого юзера - делаю его гостем
            userService.updateUserRole(driverEntity.getUser().getId(), "Guest"); // set to old User "Guest" Role
            // и устанавливаю водителю нового пользователя
            UserEntity newUser = userService.getUserEntity(driverDTO.getUserId()); // get new User for Driver
            userService.updateUserRole(newUser.getId(), "Driver"); // set new Role for new User
            driverEntity.setUser(newUser); // set new User for Driver
            driverDAO.update(driverEntity);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean deleteDriver(Long number) {
        DriverEntity driverEntity = driverDAO.getByNumber(number);
        if (driverEntity == null) {
            return false;
        } else {
            userService.updateUserRole(driverEntity.getUser().getId(), "Guest");
            driverDAO.delete(driverEntity);
            return true;
        }
    }

    @Override
    public List<DriverDTO> getDriverList() {
        List<DriverDTO> driverDTOList = new ArrayList<>();
        driverDAO.getAll().forEach(driverEntity -> driverDTOList.add(modelMapper.map(driverEntity, DriverDTO.class)));
        return driverDTOList;
    }

    @Override
    public List<DriverDTO> GetAllFreeInCityWithHours(Long cityCode, int driveTime) {
        List<DriverEntity> allFreeInCity = driverDAO.getAllInCityWithoutOrder(cityCode);
        List<DriverEntity> allFreeInCityAndAvailable = null; // available for driveTime
        // calculate if trip will end this month:
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfNextMonth = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()), LocalTime.MIDNIGHT);
        float daysUnderway = (float) driveTime / DriverService.SHIFT_HOURS;
        int hoursUnderway = Math.round(daysUnderway*24);
        if(now.plus(hoursUnderway, ChronoUnit.HOURS).isBefore(firstDayOfNextMonth)){
            allFreeInCityAndAvailable = allFreeInCity.stream()
                    .filter(driver -> (DriverService.MAX_HOURS - driver.getHours()) >= driveTime)
                    .collect(Collectors.toList());
        }else{
            long hoursUnderwayThisMonth = ChronoUnit.HOURS.between(now,firstDayOfNextMonth);
            long driveTimeThisMonth = hoursUnderwayThisMonth * DriverService.SHIFT_HOURS / 24;
            allFreeInCityAndAvailable = allFreeInCity.stream()
                    .filter(driver -> (DriverService.MAX_HOURS - driver.getHours()) >= driveTimeThisMonth)
                    .collect(Collectors.toList());
        }
        List<DriverDTO> driverDTOList = new ArrayList<>();
        allFreeInCityAndAvailable.forEach(driverEntity -> driverDTOList.add(modelMapper.map(driverEntity, DriverDTO.class)));
        return driverDTOList;
    }

    @Override
    @Transactional
    public void setDriverShift(Long driverNumber, boolean isOnShift){
        DriverEntity driverEntity = driverDAO.getByNumber(driverNumber);
        if(isOnShift){ // if driver start his shift -> set this time
            driverEntity.setLastShiftTime(LocalDateTime.now());
            driverEntity.setStatus(DriverDTO.isOnShiftStatuses[1]);
        }else{ // else -> calculate how much was his shift
            LocalDateTime shiftStartedTime = driverEntity.getLastShiftTime();
            LocalDateTime firstDayOfThisMonth = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIDNIGHT);
            // if month not change (shift was started this month)
            if(shiftStartedTime.isAfter(firstDayOfThisMonth)){
                int workingHours = (int) ChronoUnit.HOURS.between(shiftStartedTime,LocalDateTime.now());
                driverEntity.setHours(driverEntity.getHours() + workingHours);
            }else{//if month changed
                int workingHours = (int) ChronoUnit.HOURS.between(firstDayOfThisMonth,LocalDateTime.now());
                driverEntity.setHours(driverEntity.getHours() + workingHours);
            }
            // change status to indicate that driver isn't working anymore
            driverEntity.setStatus(DriverDTO.isOnShiftStatuses[0]);
        }
        driverEntity.setOnShift(isOnShift);
        driverDAO.update(driverEntity);
    }

    @Override
    public void filterDriverList(List<DriverDTO> sourceList, List<DriverDTO> subtractDrivers) {
        if(subtractDrivers != null){
            List<Long> subtractDriversNumbers = subtractDrivers.stream()
                    .map(DriverDTO::getNumber)
                    .collect(Collectors.toList());
            sourceList.removeIf(driver->subtractDriversNumbers.contains(driver.getNumber()));
        }
    }

    @Override
    @Transactional
    public void setStatus(Long driverNumber, String newStatus) {
        DriverEntity driverEntity = driverDAO.getByNumber(driverNumber);
        driverEntity.setStatus(newStatus);
        driverDAO.update(driverEntity);
    }
}
