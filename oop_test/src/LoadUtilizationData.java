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
    private List<String> listData = new ArrayList<String>();

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
        return formatData(listData);
    }
    
    // 特定のキーに基づいて整形されたデータを返却
    public Map<String, List<String>> getFormattedData(String column, String key) throws Exception{
        this.initializeDataFormat(column);
        return formatData(listData, column, key);
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

    // 入力されたキーが存在するか確認
    private void initializeDataFormat(String column) throws Exception{
        if(!this.PROP_NAMES.contains(column)){
            throw new Exception("指定されたカラムが存在しません。カラム：" + column);
        }
    }

    // csvデータを各項目毎に分けてMapに詰め替え
    private Map<String, List<String>> formatData(List<String> dataList){
        // mapを初期化
        Map<String, List<String>> formattedData = new HashMap<String, List<String>>();
        for(String prop : PROP_NAMES){
            formattedData.put(prop, new ArrayList<String>());
        }

        for(String rowData: dataList){
            // データを1行分ずつ取得
            String[] rowDataArr = rowData.split(",");
            //Arrays.asList(rowDataArr).stream().forEach(System.out::println);
            //System.out.println();
            // Map更新
            for(int i=0; i < rowDataArr.length; i++){
                //System.out.println(rowDataArr[i]);
                String currentProp = PROP_NAMES.get(i);
                List<String> data = formattedData.get(currentProp);
                
                data.add(rowDataArr[i]);
                formattedData.put(currentProp, data);
            }
        }
        return formattedData;
    }

    // csvデータを特定のカラムの特定の値をキーとして整理
    private Map<String, List<String>> formatData(List<String> dataList, String columnName, String key){
        // カラム毎に分けてMapに詰め替え
        Map<String, List<String>> data = this.formatData(dataList);
        // 検索対象カラムのデータを取得
        List<String> column = data.get(columnName);
        List<Integer> indexList = new ArrayList<Integer>();
        Map<String, List<String>> formattedData = new HashMap<String, List<String>>();

        // 検索対象キーを含むインデックスを取得
        for(int i=0; i < column.size(); i++){
            System.out.println(key + " " + column.get(i));
            if(column.get(i).equals(key)){
                indexList.add(i);
            }
        }
        
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            List<String> formattedList = new ArrayList<String>();
            List<String> values = entry.getValue();
            // キーを含む行のみ抽出する
            for(int j=0; j < column.size(); j++){
                if(indexList.contains(j)){
                    formattedList.add(values.get(j));
                }
            }
            // 出力するMapに追加
            formattedData.put(entry.getKey(), formattedList);
        }
        return formattedData;
    }
}