package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dao.CityDAO;
import com.gorbunovey.logisticapp.dao.DriverDAO;
import com.gorbunovey.logisticapp.dao.RoleDAO;
import com.gorbunovey.logisticapp.dao.UserDAO;
import com.gorbunovey.logisticapp.dto.DriverDTO;
import com.gorbunovey.logisticapp.entity.DriverEntity;
import com.gorbunovey.logisticapp.entity.DriverHistoryEntity;
import com.gorbunovey.logisticapp.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public List<DriverDTO> GetAllFreeInCityWithHours(Long cityCode, int hours) {
        List<DriverEntity> allFreeInCity = driverDAO.getAllInCityWithoutOrder(cityCode);

        System.out.println("[-------------allFreeInCity-------------]");
        allFreeInCity.forEach(System.out::println);
        this.getDriverHours(allFreeInCity.get(0));
        return null;
    }

    // сколько водитель наработал в месяце
    private int getDriverHours(DriverEntity driverEntity){
        System.out.println("#######-------------getDriverHours(DriverEntity driverEntity)-------------#######");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfThisMonth = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIDNIGHT);
        System.out.println("-------firstDayOfThisMonth-----"+firstDayOfThisMonth);

        List<DriverHistoryEntity> thisMonthHistory = driverEntity.getDriverHistory().stream()
                .filter(h->h.getStatusTime().isAfter(firstDayOfThisMonth))
                //.sorted((DriverHistoryEntity h1, DriverHistoryEntity h2) -> h1.getStatusTime().compareTo(h2.getStatusTime()))
                .sorted(Comparator.comparing(DriverHistoryEntity::getStatusTime))
                .collect(Collectors.toList());
        if(thisMonthHistory == null || thisMonthHistory.isEmpty()) return 0;
        String lastStatus = thisMonthHistory.get(0).getStatus();
        for(DriverHistoryEntity history: thisMonthHistory){
            String tempStatus = history.getStatus();
            switch (tempStatus){
                case "free": break;
                case "first": break;
                case "second": break;
                case "loading": break;
                case "resting": break;
            }
        }

        return -Integer.MAX_VALUE;
    }
}
