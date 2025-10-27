package racingcar;

import camp.nextstep.edu.missionutils.Console;

import java.io.IOException;
import java.util.Map;

public class RacingController {
    private final Validator validator;
    private final RacingService racingService;
    private final OutputFormatter outputFormatter;

    public RacingController(Validator validator,
                            RacingService racingService, OutputFormatter outputFormatter){
        this.validator = validator;
        this.racingService = racingService;
        this.outputFormatter = outputFormatter;
    }

    public void run() {

        // 자동차 이름 입력 받기
        System.out.println("경주할 자동차 이름을 입력하세요." +
                "(이름은 쉼표(,) 기준으로 구분)");
        String[] carNames =  validator.validCarNames(Console.readLine());

        // 경주 회차 입력 받기
        System.out.println("시도할 횟수는 몇 회인가요?");
        int round = validator.validRoundNumber(Console.readLine());

        // 자동차 목록 생성
        racingService.startRace(carNames, round);

        // 차수별 실행 결과 작성
        for(int i=0; i<round; i++){
            Map<String,Integer> record = racingService.runEachRace();
            outputFormatter.getRoundRecord(record);
        }

        // 우승자 안내 문구 작성
        outputFormatter.getWinners(racingService.selectWinners());

        // 최종 문구 출력
        System.out.println(outputFormatter.getStringBuilder());
    }


}
