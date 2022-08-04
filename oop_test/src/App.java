import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
                /**
         * 1. 利用実績読取り
         * 2. 利用施設ごとにデータをまとめる
         * 3. 店の選択
         * 4. モジュールの作成
         * 5. ヘッダ出力
         * 6. 各利用状況出力
         */

        String dataPath = "施設利用実績データ\\FacilityUtilizationData.csv";
        Scanner scanner = new Scanner(System.in);
        /**
         * 問題1
         * 　CSVを読み込む
         */
        LoadUtilizationData lud = new LoadUtilizationData(dataPath);
        
        /**
         * 問題2
         *  利用施設ごとにデータをまとめる
         */
        System.out.print("店舗名を選択してください：");
        String store = scanner.nextLine();


        Map<String, List<String>> data2;
        try {
            data2 = lud.getFormattedData("利用店", store);
        } catch (Exception e) {
            e.printStackTrace();
            scanner.close();
            return;
        }


        // データ表示テスト
        for (Map.Entry<String, List<String>> entry : data2.entrySet()) {
            List<String> tmp = entry.getValue();
            tmp.stream().limit(20L).forEach(System.out::println);
            System.out.println("a");
        }

        scanner.close();
    }
}
