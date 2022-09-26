package com.example.dmzserver;

import com.asus.healthhub.asusutils.ASUSDecrypt;
import org.junit.jupiter.api.Test;

public class TestDecrypt {
    @Test
    void test_string_decrypt() {

        String src =
                "0lwLz/T1u/Kqx4hnRtGb+YXx6s+TDvYih1AVY1R2UXrB7pmMQHf3rEBWXdhePC6bHSw8kclVBv6tJRF4EMuOdcB837L8stM2mBqDq1HB7T9xxkM2lSfILfDLPtVEmKazAMdXJwgrlX9GQpv5zB56An3nXLej6Hcn+4ep4A1MwuvMeaKZXy9sxDZiQeyt7Oklsx90f9OzXfAd865ye+jLT/g19BqYMYpFxUaBKEWh8RK9DtKrr68GZPbJt7z8n59rKlb9+m/1tLHvRZHm4UFdHtCgmS6wYDjufF/+5q6Ro6jQn+lD1VfMYaRNN/BvDSLOWxwO1UzHQ28WXjqIVDiVpfMG/2LrgdpSve2MWy2rkwM=";
        String dest = ASUSDecrypt.DecrypeData( src);
        System.out.println(dest);

    }
}
