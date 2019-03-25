package com.streak.referral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferralReport {

    private Map<String, String> userMap = new HashMap<>();
    private Map<String, Map<String, Integer>> perMonthPerUseCountMap = new HashMap<>();

    private List<String> cleanUpQueue = new ArrayList<>();

    public void printReport(List<Referral> referrals) {
        if (referrals == null || referrals.isEmpty()) {
            System.out.println("No Referral Data");
            return;
        }

        for (Referral referral : referrals) {
            userMap.put(referral.getName(), referral.getReferredBy());
            if (hasReferredBy(referral.getReferredBy())) {
                cleanUpQueue.add(referral.getName());
            }
            addReferralToReportCountMap(referral);
        }

        for (String name : cleanUpQueue) {
            findAndSetRootReferral(name);
        }
        printCountMap();
    }

    private void printCountMap() {
        for (Map.Entry<String, Map<String, Integer>> keyset : perMonthPerUseCountMap.entrySet()) {
            if (keyset.getValue().isEmpty()) {
                continue;
            }
            System.out.println(keyset.getKey());
            Map<String, Integer> m = consolidateCountByRootReferral(keyset.getValue());
            for (Map.Entry<String, Integer> kvp : m.entrySet()) {
                System.out.println(kvp.getKey() + ": " + kvp.getValue());
            }
            System.out.println();
        }
    }

    private void addReferralToReportCountMap(Referral referral) {
        String date = getClosingDateByMonth(referral);

        if (!perMonthPerUseCountMap.containsKey(date)) {
            perMonthPerUseCountMap.put(date, new HashMap<>());
        }

        Map<String, Integer> countMap = perMonthPerUseCountMap.get(date);

        if (hasReferredBy(referral.getReferredBy()) && !countMap.containsKey(referral.getReferredBy())) {
            countMap.put(referral.getReferredBy(), 0);
        }
        countMap.computeIfPresent(referral.getReferredBy(), (k, v) -> v + 1);
    }

    private Map<String, Integer> consolidateCountByRootReferral(Map<String, Integer> map) {

        Map<String, Integer> consolidateMap = new HashMap<>();

        for (Map.Entry<String, Integer> kvp : map.entrySet()) {
            String rootReferral = userMap.get(kvp.getKey());
            if (rootReferral == null) {
                consolidateMap.put(kvp.getKey(), kvp.getValue());
                continue;
            }
            if (consolidateMap.containsKey(rootReferral)) {
                consolidateMap.computeIfPresent(rootReferral, (k, v) -> v + 1);
            } else {
                consolidateMap.put(rootReferral, kvp.getValue());
            }
        }
        
        return consolidateMap;
    }

    private boolean hasReferredBy(String referredByName) {
        return referredByName != null && !referredByName.isEmpty();
    }

    private String getClosingDateByMonth(Referral referral) {
        return referral.getCloseDate().substring(0, 2) + "/" + referral.getCloseDate().substring(7, 10);
    }

    private String findAndSetRootReferral(String name) {
        if (!hasReferredBy(userMap.get(name))) {
            return name;
        }
        String root = findAndSetRootReferral(userMap.get(name));
        userMap.put(name, root);
        return root;
    }
}
