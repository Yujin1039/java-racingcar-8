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
    public void saveMoves(){
        Set<String> carNames = carRecord.keySet();
        Iterator<String> carIter = carNames.iterator();

        while(carIter.hasNext()){
            String car = carIter.next();
            int score = carRecord.get(car);
            score += move();

            carRecord.put(car, score);
        }
    }

    // 자동차 이동
    public int move(){
        int randomValue = Randoms.pickNumberInRange(0,9);

        if(randomValue >= 4){
            return 1;
        }else {
            return 0;
        }
    }

    // 우승자 선정
    public List<String> selectWinners(Map<String, Integer> carRecord){
        int highestScore = getHighestScore();
        List<String> winners = new ArrayList<>();
        Set<String> carNames = carRecord.keySet();
        Iterator<String> carIter = carNames.iterator();

        while(carIter.hasNext()){
            String car = carIter.next();
            int score = carRecord.get(car);

            if(score == highestScore){
                winners.add(car);
            }
        }

        return winners;
    }

    // 최고 점수 산정
    public int getHighestScore(){
        int highestScore = 0;
        Set<String> carNames = carRecord.keySet();
        Iterator<String> carIter = carNames.iterator();

        while(carIter.hasNext()){
            String car = carIter.next();
            int score = carRecord.get(car);

            if(score > highestScore){
                highestScore = score;
            }
        }

        return highestScore;
    }
}
