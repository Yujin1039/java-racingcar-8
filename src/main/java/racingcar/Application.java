package racingcar;

public class Application {
    public static void main(String[] args) {
        Validator validator = new Validator();
        RacingService racingService = new RacingService();

        // 실행시 필요한 클래스 주입
        RacingController controller = new RacingController(validator, racingService);

        // 실행
        controller.run();
    }
}
