package com.rh.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rh.persistence.DBPersister;

public class DBConfigHolder {
    private Map<String, String> operatorKeyMap;
    private Map<String, String> operatorKeyMapGETAPI;
    private Map<String, Integer> operatorIdMap;
    private Map<String, Integer> circleIdMap;
    private Map<String, Integer> postPaidOperatorIdMap;
    private Map<String, Integer> dthOperatorIdMap;
    private Map<String, String> operatorKeyMapOxygenPrepaid;
    private Map<String, String> operatorKeyMapOxygenPostpaid;
    private Map<String, String> operatorKeyMapOxygenPrepaid_1;
    private Map<String, String> operatorKeyMapOxygenPostpaid_1;
    private Map<String, String> circleKeyMapOxygen;
    private Map<String, String> operatorKeyMapOxygenDth;
    private Map<Integer, Integer> validityPriceMapNexgenTV;
    private Map<Integer, Integer> amountfirstRedeemBonus;
    private List<Long> barredEttId;
    Map<String, String> props = new HashMap<String, String>();
    private boolean IS_GETAPI_ENABLE;

    public DBConfigHolder(DBPersister db) throws IOException {
        this.props = db.getConfig();
        
        try {
            int i;
            System.out.println("loading configuration from db.....");
            String[] operators = this.props.get("OPERATOR").split(",");
            String[] operatorKeys = this.props.get("OPERATOR_KEY").split(",");
            String[] operatorKeysGETAPI = this.props.get("OPERATOR_KEY_GETAPI").split(",");
            String[] operatorIds = this.props.get("OPERATOR_ID").split(",");
            String[] circles = this.props.get("OPERATOR_CIRCLE").split(",");
            String[] circleIds = this.props.get("CIRCLE_ID").split(",");
            String[] operatorPostPaid = this.props.get("OPERATOR_POSTPAID").split(",");
            String[] operatorIdPostPaid = this.props.get("OPERATOR_ID_POSTPAID").split(",");
            String[] operatorDth = this.props.get("OPERATOR_DTH").split(",");
            String[] operatorIdDth = this.props.get("OPERATOR_ID_DTH").split(",");
            String[] circleKeyOxygen = this.props.get("CIRCLE_KEY_OXYGEN").split(",");
            String[] operatorKeyOxygenPrepaid = this.props.get("OPERATOR_KEY_OXYGEN_PREPAID").split(",");
            String[] operatorKeyOxygenPostpaid = this.props.get("OPERATOR_KEY_OXYGEN_POSTPAID").split(",");
            String[] operatorKeyOxygenPrepaid_1 = this.props.get("OPERATOR_KEY_OXYGEN_PREPAID_1").split(",");
            String[] operatorKeyOxygenPostpaid_1 = this.props.get("OPERATOR_KEY_OXYGEN_POSTPAID_1").split(",");
            String[] operatorKeyOxygenDth = this.props.get("OPERATOR_KEY_OXYGEN_DTH").split(",");
            String[] nexGenValidity = this.props.get("NEXGENTV_VALIDITY").split(",");
            String[] nexGenPrice = this.props.get("NEXGENTV_PRICE").split(",");
            String[] firstRedeemBonus = this.props.get("FIRST_REDEEM_BONUS").split("#");
            this.operatorKeyMap = new HashMap<String, String>();
            this.operatorKeyMapGETAPI = new HashMap<String, String>();
            this.operatorIdMap = new HashMap<String, Integer>();
            this.circleIdMap = new HashMap<String, Integer>();
            this.postPaidOperatorIdMap = new HashMap<String, Integer>();
            this.dthOperatorIdMap = new HashMap<String, Integer>();
            this.operatorKeyMapOxygenPrepaid = new HashMap<String, String>();
            this.operatorKeyMapOxygenPostpaid = new HashMap<String, String>();
            this.operatorKeyMapOxygenPrepaid_1 = new HashMap<String, String>();
            this.operatorKeyMapOxygenPostpaid_1 = new HashMap<String, String>();
            this.circleKeyMapOxygen = new HashMap<String, String>();
            this.operatorKeyMapOxygenDth = new HashMap<String, String>();
            this.validityPriceMapNexgenTV = new HashMap<Integer, Integer>();
            this.amountfirstRedeemBonus = new HashMap<Integer, Integer>();
            
            for (i = 0; i < firstRedeemBonus[0].split(",").length; ++i) {
                this.amountfirstRedeemBonus.put(Integer.valueOf(firstRedeemBonus[0].split(",")[i]), Integer.valueOf(firstRedeemBonus[1].split(",")[i]));
            }
            
            
            for (i = 0; i < operators.length; ++i) {
                this.operatorKeyMap.put(operators[i].toUpperCase(), operatorKeys[i]);
                this.operatorKeyMapGETAPI.put(operators[i].toUpperCase(), operatorKeysGETAPI[i]);
                this.operatorIdMap.put(operators[i].toUpperCase(), Integer.valueOf(operatorIds[i]));
                this.operatorKeyMapOxygenPrepaid.put(operators[i].toUpperCase(), operatorKeyOxygenPrepaid[i]);
                this.operatorKeyMapOxygenPrepaid_1.put(operators[i].toUpperCase(), operatorKeyOxygenPrepaid_1[i]);
            }
            for (i = 0; i < circles.length; ++i) {
                this.circleIdMap.put(circles[i].toUpperCase(), Integer.valueOf(circleIds[i]));
                this.circleKeyMapOxygen.put(circles[i].toUpperCase(), circleKeyOxygen[i]);
            }
            for (i = 0; i < operatorPostPaid.length; ++i) {
                this.postPaidOperatorIdMap.put(operatorPostPaid[i].toUpperCase(), Integer.valueOf(operatorIdPostPaid[i]));
                this.operatorKeyMapOxygenPostpaid.put(operatorPostPaid[i].toUpperCase(), operatorKeyOxygenPostpaid[i]);
                this.operatorKeyMapOxygenPostpaid_1.put(operatorPostPaid[i].toUpperCase(), operatorKeyOxygenPostpaid_1[i]);
            }
            for (i = 0; i < operatorDth.length; ++i) {
                this.dthOperatorIdMap.put(operatorDth[i], Integer.valueOf(operatorIdDth[i]));
                this.operatorKeyMapOxygenDth.put(operatorDth[i], operatorKeyOxygenDth[i]);
            }
            for (i = 0; i < nexGenPrice.length; ++i) {
                this.validityPriceMapNexgenTV.put(Integer.valueOf(nexGenPrice[i]), Integer.valueOf(nexGenValidity[i]));
            }
            String I_GA_E = this.props.get("IS_GETAPI_ENABLE").trim();
            if (I_GA_E.equalsIgnoreCase("TRUE") || I_GA_E.equalsIgnoreCase("YES")) {
                this.IS_GETAPI_ENABLE = true;
            }
            if (this.props.get("BARRED_USER_CHECK") != null && this.props.get("BARRED_USER_ETTID") != null && this.props.get("BARRED_USER_CHECK").equals("true") && !this.props.get("BARRED_USER_ETTID").equals("")) {
                String[] barredIds = this.props.get("BARRED_USER_ETTID").split(",");
                this.barredEttId = new ArrayList<Long>(barredIds.length);
                for (int i2 = 0; i2 < barredIds.length; ++i2) {
                    this.barredEttId.add(Long.valueOf(barredIds[i2]));
                }
                System.out.println("Barred ettIds : " + this.barredEttId.toString());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Map<String, String> getOperatorKeyMap() {
        return this.operatorKeyMap;
    }

    public String getOperatorKey(String operator) {
        return this.operatorKeyMap.get(operator);
    }

    public Map<String, String> getOperatorKeyMapGETAPI() {
        return this.operatorKeyMapGETAPI;
    }

    public String getOperatorKeyGETAPI(String operator) {
        return this.operatorKeyMapGETAPI.get(operator);
    }

    public Map<Integer, Integer> getAmountfirstRedeemBonus() {
        return this.amountfirstRedeemBonus;
    }

    public Integer getfirstRedeemBonus(int amount) {
        return this.amountfirstRedeemBonus.get(amount);
    }

    public Map<String, Integer> getOperatorIdMapI() {
        return this.operatorIdMap;
    }

    public Integer getValidityPriceMapNexgenTV(int amount) {
        return this.validityPriceMapNexgenTV.get(amount);
    }

    public Integer getOperatorId(String operator) {
        return this.operatorIdMap.get(operator);
    }

    public Integer getPostPaidOperatorId(String operator) {
        return this.postPaidOperatorIdMap.get(operator);
    }

    public Integer getDthOperatorId(String operator) {
        return this.dthOperatorIdMap.get(operator);
    }

    public String getOperatorKeyMapOxygenPrepaid(String operator) {
        return this.operatorKeyMapOxygenPrepaid.get(operator.toUpperCase());
    }

    public String getOperatorKeyMapOxygenPostpaid(String operator) {
        return this.operatorKeyMapOxygenPostpaid.get(operator.toUpperCase());
    }

    public String getOperatorKeyMapOxygenPrepaid_1(String operator) {
        return this.operatorKeyMapOxygenPrepaid_1.get(operator.toUpperCase());
    }

    public String getOperatorKeyMapOxygenPostpaid_1(String operator) {
        return this.operatorKeyMapOxygenPostpaid_1.get(operator.toUpperCase());
    }

    public String getCircleKeyMapOxygen(String circle) {
        return this.circleKeyMapOxygen.get(circle.toUpperCase());
    }

    public String getOperatorKeyMapOxygenDth(String operator) {
        return this.operatorKeyMapOxygenDth.get(operator.toUpperCase());
    }

    public Map<String, Integer> getCircleIdMap() {
        return this.circleIdMap;
    }

    public Integer getCircleId(String circle) {
        return this.circleIdMap.get(circle);
    }

    public Map<String, String> getProperties() {
        return this.props;
    }

    public boolean getIsGetApi() {
        return this.IS_GETAPI_ENABLE;
    }

    public boolean getBarredStatus(Long id) {
        if (this.barredEttId.contains(id)) {
            return true;
        }
        return false;
    }
}