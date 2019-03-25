package com.streak.referral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReferralReport {

    private Map<String, String> userMap = new HashMap<>();
    private Map<String, Map<String, Integer>> countPerMonthPerUserMap = new HashMap<>();

    private List<String> cleanUpQueue = new ArrayList<>();

    public void printReport(List<Referral> referrals) {
        if (referrals == null || referrals.isEmpty()) {
            System.out.println("No Referral Data");
        }

        for (Referral referral : referrals) {
            userMap.put(referral.getName(), referral.getReferredBy());
            if (hasReferredBy(referral)) {
                cleanUpQueue.add(referral.getName());
            }
            addReferralToReportMap(referral);
        }

        for (String name : cleanUpQueue) {
            findAndSetRootReferral(name);
        }
        printCountMap();
    }

    private void printCountMap() {
        for (Map.Entry<String, Map<String, Integer>> keyset : countPerMonthPerUserMap.entrySet()) {
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

    private void addReferralToReportMap(Referral referral) {
        String date = getClosingDateByMonth(referral);

        if (!countPerMonthPerUserMap.containsKey(date)) {
            countPerMonthPerUserMap.put(date, new HashMap<>());
        }

        Map<String, Integer> countMap = countPerMonthPerUserMap.get(date);

        if (hasReferredBy(referral) && !countMap.containsKey(referral.getReferredBy())) {
            countMap.put(referral.getReferredBy(), 0);
        }
        countMap.computeIfPresent(referral.getReferredBy(), (k, v) -> v + 1);
    }

    private Map<String, Integer> consolidateCountByRootReferral(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> kvp : map.entrySet()) {
            String rootReferral = userMap.get(kvp.getKey());
            if (rootReferral == null) {
                continue;
            }
            if (map.containsKey(rootReferral)) {
                map.computeIfPresent(rootReferral, (k, v) -> v + 1);
            } else {
                map.put(rootReferral, kvp.getValue());
            }
            map.remove(kvp.getKey());
        }
        return map;
    }

    private boolean hasReferredBy(Referral referral) {
        return referral.getReferredBy() != null && !referral.getReferredBy().isEmpty();
    }

    private String getClosingDateByMonth(Referral referral) {
        return referral.getCloseDate().substring(0, 2) + "/" + referral.getCloseDate().substring(7, 10);
    }

    private String findAndSetRootReferral(String name) {
        if (userMap.get(name) == null || userMap.get(name).isEmpty()) {
            return name;
        }
        String root = findAndSetRootReferral(userMap.get(name));
        userMap.put(name, root);
        return root;
    }
}
