package racingcar;

import java.util.List;
import java.util.Map;

public class RacingService {
    private CarList carList;

    // 자동차 목록 객체 생성
    public void startRace(String[] carNames, int round){
        this.carList = new CarList(carNames, round);
    }

    // 자동차 경주 시행
    public Map<String,Integer> runEachRace(){
        carList.saveMoves();
        return carList.getCarRecord();
    }

    // 우승자 선정
    public List<String> selectWinners(){
        return carList.selectWinners(carList.getCarRecord());
    }
}
