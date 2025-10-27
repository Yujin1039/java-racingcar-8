package racingcar;

import camp.nextstep.edu.missionutils.Console;

public class RacingController {
    private final Validator validator;
    private final RacingService racingService;

    public RacingController(Validator validator, RacingService racingService){
        this.validator = validator;
        this.racingService = racingService;
    }

    public void run(){

        // 자동차 이름 입력 받기
        System.out.println("경주할 자동차 이름을 입력하세요." +
                "(이름은 쉼표(,) 기준으로 구분)");
        String[] carNames =  validator.validCarNames(Console.readLine());

        // 경주 회차 입력 받기
        System.out.println("시도할 횟수는 몇 회인가요?");
        int round = validator.validRoundNumber(Console.readLine());

        // 자동차 목록 생성

        // 차수별 실행 결과


        // 우승자 안내 문구 출력
    }


}
