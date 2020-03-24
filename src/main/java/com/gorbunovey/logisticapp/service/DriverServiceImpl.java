package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.CityDAO;
import com.gorbunovey.logisticapp.dao.DriverDAO;
import com.gorbunovey.logisticapp.dao.RoleDAO;
import com.gorbunovey.logisticapp.dao.UserDAO;
import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.UserEntity;
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
import java.util.Comparator;
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
        driverEntity.setCity(cityDAO.getByCode(driverDTO.getCityCode())); // set City
        UserEntity userEntity = userService.getUserEntityByNumber(driverDTO.getUserNumber()); // get chosen User
        userService.updateUserRole(driverDTO.getUserNumber(), "Driver"); // set new Role
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
            driverEntity.setCity(cityDAO.getByCode(driverDTO.getCityCode())); // set new City
            // обновляю поля, которые требуют каскадных изменений, не доступных с этой стороны:
            // т.е. обновляю роль у старого юзера - делаю его гостем
            userService.updateUserRole(driverEntity.getUser().getNumber(), "Guest"); // set to old User "Guest" Role
            // и устанавливаю водителю нового пользователя
            UserEntity newUser = userService.getUserEntityByNumber(driverDTO.getUserNumber()); // get new User for Driver
            userService.updateUserRole(newUser.getNumber(), "Driver"); // set new Role for new User
            driverEntity.setUser(newUser); // set new User for Driver
            driverDAO.update(driverEntity);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean deleteDriver(Long number) {
        DriverEntity driverEntity = driverDAO.getByNumber(number);
        System.out.println("--------deleteDriver------>" + driverEntity);
        if (driverEntity == null) {
            return false;
        } else {
            userService.updateUserRole(driverEntity.getUser().getNumber(), "Guest");
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
        }
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

}
