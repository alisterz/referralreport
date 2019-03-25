import com.google.gson.Gson;
import com.streak.referral.Referral;
import com.streak.referral.ReferralReport;

import java.util.Arrays;

public class ReferralReportApplication {
  private static Gson gson = new Gson();
  private static String REFERRAL_IMPORT =
      "[\n"
          + "      {\n"
          + "        \"name\": \"Acme, Inc.\", \"close_date\": \"01-12-2018\", \"referred_by\": \"Sprockets\"\n"
          + "      },\n"
          + "      {\n"
          + "        \"name\": \"Sprockets\", \"close_date\": \"01-09-2018\", \"referred_by\": \"Dunder Mifflin\"\n"
          + "      },\n"
          + "      {\n"
          + "        \"name\": \"Initech\", \"close_date\": \"01-01-2018\"\n"
          + "      },\n"
          + "      {\n"
          + "        \"name\": \"Initrode\", \"close_date\": \"02-02-2018\", \"referred_by\": \"Initech\"\n"
          + "      },\n"
          + "      {\n"
          + "        \"name\": \"L&P Construction\", \"close_date\": \"01-19-2018\", \"referred_by\": \"Initech\"\n"
          + "      },\n"
          + "      {\n"
          + "        \"name\": \"Dunder Mifflin\", \"close_date\": \"12-01-2017\"\n"
          + "      }\n"
          + "    ]";

  public static void main(String[] args) {
    Referral[] referrals = gson.fromJson(REFERRAL_IMPORT, Referral[].class);
    ReferralReport rp = new ReferralReport();
    rp.printReport(Arrays.asList(referrals));
  }
}
