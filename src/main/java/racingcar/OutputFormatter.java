package racingcar;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OutputFormatter {
    private final StringBuilder sb = new StringBuilder("실행결과\n");

    // 차수별 실행 결과
    public void getRoundRecord(Map<String, Integer> carRecord){
        Set<String> carNames = carRecord.keySet();
        Iterator<String> carIter = carNames.iterator();

        if(carIter.hasNext()){
            String car = carIter.next();
            String format = "-";
            sb.append(car+" : "+format.repeat(carRecord.get(car))+"\n");
        }
        sb.append("\n");
    }

    // 우승자 안내 문구
}
