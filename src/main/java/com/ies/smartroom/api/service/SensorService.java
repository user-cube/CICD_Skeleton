package com.ies.smartroom.api.service;

import com.ies.smartroom.api.entities.internal.Average;
import com.ies.smartroom.api.entities.internal.Sensor;
import com.ies.smartroom.api.repositories.SensorRepository;

import java.time.LocalDate;
import java.util.*;


public abstract class SensorService {

    private SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<?> findAll(long home) {
        return sensorRepository.findByHome(home);
    }

    public List<?> getByDay(String date, long home) {
        try {
            return sensorRepository.findByHomeAndRange(
                    home,
                    startDayToTimeStamp(date),
                    endDayToTimeStamp(date)
            );
        } catch (Exception e) {
            return null;
        }
    }

    public List<?> getNow(long home) {
        return getByDay(LocalDate.now().toString(), home);
    }

    public List<?> getByDateRange(String from, String to, long home) {
        try {
            String start = startDayToTimeStamp(from);
            String end = endDayToTimeStamp(to);
            List t = sensorRepository.findByHomeAndRange(
                    home,
                    start,
                    end);
            return t;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<?> getLastWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getByDateRange(from, to, home);
    }

    /*     Average           */

    public Average getAverageDay(String day, long home) {
        try {
            List sensors = sensorRepository.findByHomeAndRange(home,
                    startDayToTimeStamp(day),
                    endDayToTimeStamp(day));
            double count = sensors.size();
            Iterator<Sensor> TIterator = sensors.iterator();
            double avg = 0;
            Sensor sensorNext = null;
            while (TIterator.hasNext()) {
                sensorNext = TIterator.next();
                avg += sensorNext.getValue();
            }
            if (sensorNext == null || avg == 0) {
                return new Average(home, null, day, null);
            }
            return new Average(sensorNext.getHome(), sensorNext.getRoom(), day, avg/count);
        } catch (Exception ex) {
            return null;
        }
    }

    public Average getAverageToday(long home) {
        return getAverageDay(LocalDate.now().toString(), home);
    }

    public List<Average> getAverageRange(String from, String to, long home) {
        LocalDate dateTo;
        try {
            dateTo = LocalDate.parse(to);
        } catch (Exception ex) {
            dateTo = LocalDate.now();
        }
        try {
            LocalDate dateFrom = LocalDate.parse(from);
            List listVariable = new ArrayList();
            while (!dateFrom.isEqual(dateTo.plusDays(1))) {

                listVariable.add(
                        getAverageDay(
                                dateFrom.toString(),
                                home)
                );

                dateFrom = dateFrom.plusDays(1);
            }
            return listVariable;
        } catch (
                Exception ex) {
            return null;
        }
    }

    public List<Average> getAverageWeek(long home) {
        LocalDate today = LocalDate.now();
        String to = today.toString();
        String from = today.minusDays(7).toString();
        return getAverageRange(from, to, home);
    }

    private String endDayToTimeStamp(String day) {
        return day + " 23:59:59";
    }

    private String startDayToTimeStamp(String day) {
        return day + " 00:00:00";
    }
}