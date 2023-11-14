package converter_JSONToJava;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
	
	public static List<List<String>> getDataAsRows(Map<String, Object> data, String parentKey) {
        List<List<String>> rows = new ArrayList<>();
        for (String key : data.keySet()) {
            if (data.containsKey(key)) {
                String currentKey = parentKey != null && !parentKey.isEmpty() ? parentKey + "." + key : key;
                if (data.get(key) instanceof Map && !(data.get(key) instanceof List)) {
                    // Handle nested objects
                    rows.add(List.of(currentKey, ""));
                    rows.addAll(getDataAsRows((Map<String, Object>) data.get(key), currentKey));
                } else if (data.get(key) instanceof List) {
                    // Handle arrays
                    rows.add(List.of(currentKey, ""));
                    List<Object> array = (List<Object>) data.get(key);
                    // Create headers for the array elements
                    if (!array.isEmpty() && array.get(0) instanceof Map) {
                        Map<String, Object> firstElement = (Map<String, Object>) array.get(0);
                        List<String> headers = new ArrayList<>(firstElement.keySet());
                        rows.add(headers);
                        // Create rows for each array element
                        for (Object item : array) {
                            Map<String, Object> element = (Map<String, Object>) item;
                            rows.add(headers.stream().map(header -> element.get(header).toString()).toList());
                        }
                    } else {
                        // If the array elements are not objects, treat them as simple values
                        for (Object item : array) {
                            rows.add(List.of(currentKey, item.toString()));
                        }
                    }
                } else {
                    rows.add(List.of(currentKey, data.get(key).toString()));
                }
            }
        }
        return rows;
    }
	
	public static JSONObject generate_JSON() {
		// Your JSON string
				String jsonString = "{" +
					    "\"ai\": {" +
					        "\"Company\": \"PT KINO INDONESIA TBK.\"," +
					        "\"Address\": \"Kino Tower Lt.17 JI. Jalur Sutera Alam Sutera. Panunggangan Timur Pinang Kota Tangerang, Banten\"," +
					        "\"Nomor Packing List\": \"1400146569\"," +
					        "\"Packing List\": \"1 from 1\"," +
					        "\"Ship To Party\": \"GEMA REIJEKI, CV - MALUKU\"," +
					        "\"Alamat Kirim\": \"JL. LAKSDYA LEO WATTIMENA RT. 025/006 BAGUALA/PASSQ, AMBON, MALUK AMBON\"," +
					        "\"Rute Sukabumi - Ambon\": \"WH Origin Sukabumi\"," +
					        "\"Ekpedisi\": \"BAHTERA SURYA\"," +
					        "\"No Polisi\": \"B 9022 KXT\"," +
					        "\"Tanggal Cetak\": \"06.09.2023\"," +
					        "\"Data\": [" +
					            "{" +
					                "\"Nomor DO\": \"1201161788\"," +
					                "\"Oty\": \"16 CAR\"," +
					                "\"UOM\": \"81,700\"," +
					                "\"Berat (KG)\": \"0245\"," +
					                "\"Kubikasi (M3)\": \"GEMA REJEKI, CV - MALUKU\"" +
					            "}," +
					            "{" +
					                "\"Nomor DO\": \"1201161789\"," +
					                "\"Oty\": \"85 CAR\"," +
					                "\"UOM\": \"272,520\"," +
					                "\"Berat (KG)\": \"1.244\"," +
					                "\"Kubikasi (M3)\": \"GEMA REJEKL CV - MALUKU\"" +
					            "}," +
					            "{" +
					                "\"Nomor DO\": \"1201161790\"," +
					                "\"Oty\": \"0 CAR\"," +
					                "\"UOM\": \"40,650\"," +
					                "\"Berat (KG)\": \"0,109\"," +
					                "\"Kubikasi (M3)\": \"GEMA REJEKI, CV - MALUKU\"" +
					            "}," +
					            // ... (Other data entries)
					        "]," +
					        "\"Grand Tot\": {" +
					            "\"Cars\": \"466\"," +
					            "\"Berat (KG)\": \"1.362,650\"," +
					            "\"Kubikasi (M3)\": \"5.961\"" +
					        "}" +
					    "}" +
					"}";

		        // Parse JSON string
		        JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(jsonString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//		        System.out.println(jsonObject);
		        return jsonObject;
	}

	public static void main(String[] args) throws JSONException {
		
		JSONObject dataObject = generate_JSON();
		
		// Call the getDataAsRows method
        JSONArray dataRows = (JSONArray) getDataAsRows((Map<String, Object>) dataObject, "");
		System.out.println("dataRows: " + dataRows);
		
		// Check if the "ai" key exists
        if (dataObject.has("ai")) {
            // Access fields directly
            String address = dataObject.getJSONObject("ai").getString("Address");
            // ... (access other fields as needed)
        } else {
            System.out.println("Key 'ai' not found in the JSON object.");
        }
    }
}
