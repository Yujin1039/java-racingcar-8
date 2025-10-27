package racingcar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    // 자동차 문자열 검증
    public String[] validCarNames(String input){
        String[] carArr = input.split(",");

        for(String car:carArr){
            if(car.length() > 5 || car.isEmpty()){ // 차 이름이 5자 이상이거나 공백인 경우
                throw new IllegalArgumentException();
            }
        }

        // 차 이름 중복 여부 확인
        Set<String> uniqueCarNames = new HashSet<>(Arrays.asList(carArr));
        if(uniqueCarNames.size() != carArr.length){
            throw new IllegalArgumentException();
        }

        return carArr;
    }

    // 경주 회차수 검증
    public int validRoundNumber(String input){
        try{
            int round = Integer.parseInt(input);
            if(round <= 0) {
                throw new Exception();
            }
            return round;

        }catch(Exception e){
            throw new IllegalArgumentException();
        }
    }
}
