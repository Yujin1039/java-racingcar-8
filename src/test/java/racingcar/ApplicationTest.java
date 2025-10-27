package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    // ===== saveMoves() 테스트 =====

    @Test
    @DisplayName("모든 자동차가 4 이상 값을 받으면 모두 1씩 전진")
    void saveMoves_모두_전진() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 5);

                    // 한 라운드 진행
                    carList.saveMoves();

                    // 검증: 모든 차가 1씩 전진 (4, 4, 4)
                    Map<String, Integer> result = carList.getCarRecord();
                    assertThat(result.get("pobi")).isEqualTo(1);
                    assertThat(result.get("crong")).isEqualTo(1);
                    assertThat(result.get("honux")).isEqualTo(1);
                },
                4, 4, 4  // pobi, crong, honux 순서대로 4
        );
    }

    @Test
    @DisplayName("모든 자동차가 3 이하 값을 받으면 모두 정지")
    void saveMoves_모두_정지() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong"};
                    CarList carList = new CarList(carNames, 5);

                    // 한 라운드 진행
                    carList.saveMoves();

                    // 검증: 모든 차가 정지 (0)
                    Map<String, Integer> result = carList.getCarRecord();
                    assertThat(result.get("pobi")).isEqualTo(0);
                    assertThat(result.get("crong")).isEqualTo(0);
                },
                2, 3  // pobi: 2, crong: 3 (모두 4 미만)
        );
    }

    @Test
    @DisplayName("일부는 전진, 일부는 정지")
    void saveMoves_혼합() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 5);

                    // 한 라운드 진행
                    carList.saveMoves();

                    // 검증: pobi와 honux는 전진, crong은 정지
                    Map<String, Integer> result = carList.getCarRecord();
                    assertThat(result.get("pobi")).isEqualTo(1);
                    assertThat(result.get("crong")).isEqualTo(0);
                    assertThat(result.get("honux")).isEqualTo(1);
                },
                5, 2, 7  // pobi: 5(전진), crong: 2(정지), honux: 7(전진)
        );
    }

    @Test
    @DisplayName("여러 라운드 진행 - 누적 점수")
    void saveMoves_여러_라운드() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong"};
                    CarList carList = new CarList(carNames, 3);

                    // 3라운드 진행
                    carList.saveMoves();  // 1라운드: pobi 4(전진), crong 2(정지)
                    carList.saveMoves();  // 2라운드: pobi 5(전진), crong 3(정지)
                    carList.saveMoves();  // 3라운드: pobi 4(전진), crong 6(전진)

                    // 검증: pobi는 3칸, crong은 1칸 전진
                    Map<String, Integer> result = carList.getCarRecord();
                    assertThat(result.get("pobi")).isEqualTo(3);
                    assertThat(result.get("crong")).isEqualTo(1);
                },
                4, 2,  // 1라운드
                5, 3,  // 2라운드
                4, 6   // 3라운드
        );
    }

    @Test
    @DisplayName("경계값 테스트 - 4는 전진, 3은 정지")
    void saveMoves_경계값() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong"};
                    CarList carList = new CarList(carNames, 1);

                    carList.saveMoves();

                    Map<String, Integer> result = carList.getCarRecord();
                    assertThat(result.get("pobi")).isEqualTo(1);  // 4는 전진
                    assertThat(result.get("crong")).isEqualTo(0); // 3은 정지
                },
                4, 3  // pobi: 4(전진), crong: 3(정지)
        );
    }


    // ===== selectWinners() 테스트 =====

    @Test
    @DisplayName("한 명의 단독 우승자")
    void selectWinners_단독_우승() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 3);

                    // 3라운드 진행
                    carList.saveMoves();
                    carList.saveMoves();
                    carList.saveMoves();

                    // 우승자 선정
                    Map<String, Integer> carRecord = carList.getCarRecord();
                    List<String> winners = carList.selectWinners(carRecord);

                    // 검증: pobi만 3번 전진, 나머지는 정지 → pobi 단독 우승
                    assertThat(winners).hasSize(1);
                    assertThat(winners).contains("pobi");
                    assertThat(carRecord.get("pobi")).isEqualTo(3);
                    assertThat(carRecord.get("crong")).isEqualTo(0);
                    assertThat(carRecord.get("honux")).isEqualTo(0);
                },
                4, 2, 1,  // 1라운드: pobi만 전진
                5, 3, 2,  // 2라운드: pobi만 전진
                6, 1, 0   // 3라운드: pobi만 전진
        );
    }

    @Test
    @DisplayName("여러 명의 공동 우승자")
    void selectWinners_공동_우승() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 2);

                    // 2라운드 진행
                    carList.saveMoves();
                    carList.saveMoves();

                    // 우승자 선정
                    Map<String, Integer> carRecord = carList.getCarRecord();
                    List<String> winners = carList.selectWinners(carRecord);

                    // 검증: pobi와 crong이 2번, honux는 0번 → pobi, crong 공동 우승
                    assertThat(winners).hasSize(2);
                    assertThat(winners).containsExactlyInAnyOrder("pobi", "crong");
                    assertThat(carRecord.get("pobi")).isEqualTo(2);
                    assertThat(carRecord.get("crong")).isEqualTo(2);
                    assertThat(carRecord.get("honux")).isEqualTo(0);
                },
                5, 6, 2,  // 1라운드: pobi, crong 전진
                4, 7, 1   // 2라운드: pobi, crong 전진
        );
    }

    @Test
    @DisplayName("모든 차가 같은 점수 - 전체 우승")
    void selectWinners_전체_우승() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 2);

                    // 2라운드 진행
                    carList.saveMoves();
                    carList.saveMoves();

                    // 우승자 선정
                    Map<String, Integer> carRecord = carList.getCarRecord();
                    List<String> winners = carList.selectWinners(carRecord);

                    // 검증: 모두 2번 전진 → 전체 우승
                    assertThat(winners).hasSize(3);
                    assertThat(winners).containsExactlyInAnyOrder("pobi", "crong", "honux");
                    assertThat(carRecord.get("pobi")).isEqualTo(2);
                    assertThat(carRecord.get("crong")).isEqualTo(2);
                    assertThat(carRecord.get("honux")).isEqualTo(2);
                },
                4, 5, 6,  // 1라운드: 모두 전진
                7, 8, 9   // 2라운드: 모두 전진
        );
    }

    @Test
    @DisplayName("아무도 움직이지 않음 - 모두 0점으로 공동 우승")
    void selectWinners_모두_0점() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong"};
                    CarList carList = new CarList(carNames, 2);

                    // 2라운드 진행
                    carList.saveMoves();
                    carList.saveMoves();

                    // 우승자 선정
                    Map<String, Integer> carRecord = carList.getCarRecord();
                    List<String> winners = carList.selectWinners(carRecord);

                    // 검증: 모두 0점 → 전체 우승
                    assertThat(winners).hasSize(2);
                    assertThat(winners).containsExactlyInAnyOrder("pobi", "crong");
                    assertThat(carRecord.get("pobi")).isEqualTo(0);
                    assertThat(carRecord.get("crong")).isEqualTo(0);
                },
                2, 1,  // 1라운드: 모두 정지
                0, 3   // 2라운드: 모두 정지
        );
    }


    // ===== 통합 테스트 (saveMoves + selectWinners) =====

    @Test
    @DisplayName("실제 게임 시나리오 - 전체 흐름")
    void 실제_게임_시나리오() {
        assertRandomNumberInRangeTest(
                () -> {
                    String[] carNames = {"pobi", "crong", "honux"};
                    CarList carList = new CarList(carNames, 5);

                    // 5라운드 진행
                    for (int i = 0; i < 5; i++) {
                        carList.saveMoves();
                    }

                    // 점수 확인
                    Map<String, Integer> carRecord = carList.getCarRecord();
                    assertThat(carRecord.get("pobi")).isEqualTo(5);   // 4번 전진
                    assertThat(carRecord.get("crong")).isEqualTo(2);  // 2번 전진
                    assertThat(carRecord.get("honux")).isEqualTo(3);  // 3번 전진

                    // 우승자 확인
                    List<String> winners = carList.selectWinners(carRecord);
                    assertThat(winners).hasSize(1);
                    assertThat(winners).contains("pobi");
                },
                // 5라운드 * 3대 = 15개의 랜덤 값
                4, 5, 6,   // 1라운드: 모두 전진
                7, 2, 8,   // 2라운드: pobi, honux 전진
                4, 1, 5,   // 3라운드: pobi, honux 전진
                5, 6, 2,   // 4라운드: pobi, crong 전진
                6, 0, 1    // 5라운드: pobi만 전진
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
