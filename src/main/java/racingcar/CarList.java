package racingcar;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class CarList {
    private final Map<String, Integer> carRecord;
    private final int round;

    // 생성자(Key: 차 이름, value: 전진 횟수)
    public CarList(String[] carNames, int round){
        this.carRecord = new HashMap<>();
        this.round = round;

        for(String car:carNames){
            carRecord.put(car.trim(),0);
        }
    }

    public Map<String, Integer> getCarRecord(){
        return carRecord;
    }

    public int getRound(){
        return round;
    }

    // 자동차 이동값 저장


    // 자동차 이동

}
