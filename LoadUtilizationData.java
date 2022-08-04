import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadUtilizationData {
    // データ項目
    private final List<String> PROP_NAMES = Arrays.asList(
        "利用店",
         "施設名", 
         "会員番号", 
         "会員氏名", 
         "利用日", 
         "利用開始時刻", 
         "利用終了時刻"
    );

    // CSVのパス
    private String pathStr;

    // 行ごとにデータを格納するList
    List<String> listData = new ArrayList<String>();

    // 整形後のデータを格納するMap
    private Map<String, List<String>> formattedData = new HashMap<String, List<String>>();


    // コンストラクタ
    public LoadUtilizationData(String pathStr){
        this.pathStr = pathStr;
        this.readCsvData();
    }


    // 行ごとにリスト化されたデータを返却
    public List<String> getListData(){
        return this.listData;
    }

    // 項目ごとに整形されたデータを返却
    public Map<String, List<String>> getFormattedData(){
        this.initializeDataFormat();
        formatData(listData);
        return this.formattedData;
    }
    
    // 特定のキーに基づいて整形されたデータを返却
    public Map<String, List<String>> getFormattedData(String key){
        this.initializeDataFormat();
        // TODO
        return this.formattedData;
    }

    // CSVデータの読み取り
    private void readCsvData() {
        try{
            File csvFile = new File(pathStr);
            if (!csvFile.exists()) {
                throw new FileNotFoundException("ファイルが見つかりませんでした。");
            }
            FileReader fileReader = new FileReader(csvFile);
            
            // CSVのデータを1行ずつ取得しListに格納する。
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while (bufferedReader.readLine() != null) {
                    this.listData.add(bufferedReader.readLine());
                }
            } finally {
                System.out.println("ファイル操作終了");
            }

        } catch(IOException e) {
            System.out.println(e);
        } 
    }

    // データ出力用のMapを初期化
    private void initializeDataFormat(){
        this.formattedData.clear();
        for(String prop : PROP_NAMES){
            this.formattedData.put(prop, new ArrayList<String>());
        }
    }

    // csvデータを各項目毎に分けてMapに詰め替え
    private void formatData(List<String> dataList){
        for(String rowData: dataList){
            // データを1行分ずつ取得
            String[] rowDataArr = rowData.split(",");
            // Map更新
            for(int i=0; i < rowDataArr.length; i++){
                String currentProp = PROP_NAMES.get(i);
                List<String> data = formattedData.get(currentProp);
                data.add(rowDataArr[i]);
                formattedData.put(currentProp, data);
            }
        }
    }

}