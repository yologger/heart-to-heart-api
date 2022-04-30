package com.yologger.heart_to_heart_api.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "test3 test3";
    }

    @GetMapping("/test4")
    public String test4() {
        return "test4 test4";
    }

    @GetMapping("/test5")
    public String test5() {
        return "test5 test5";
    }

    @GetMapping("/test6")
    public String test6() {
        return "test6";
    }
}

    kubectl create secret docker-registry regcred --namespace=h2h --docker-server=056671487005.dkr.ecr.ap-northeast-2.amazonaws.com/h2h --docker-username=AWS --docker-password=eyJwYXlsb2FkIjoibXhwZW1CRHpIZHZTRlAvRmVyZHpPUVU1YXBnNncvSXpucW5jUnlVazhEVFBaWEt3RVNZcXMrSFQrYStiYVcxb21KY2gzQnNOMTZyeG85dlJWL0FWQ1ZHRkVxVmJHWTRFbnZUcEppRmlxZzZ5WjdUK1B3ejB1aVZRc3dCYVM0Rjkzejl0V1ViNUFBem5CTVg3RU5yUjFub0VvNC8xcUZ0eUZ0NXp3N3FWc01kRkNvU1IzTkNoVW1qQm5RelNsU04wZ1J1cUt1QzRaeWdJK25UalIwSytQS3pNdFUzNjVyMFluVHVzN3paWm01aFV1OHRmWndWQ3lTVHlWKzNaUzhLT09GRUUySnRGSjc0Q3lmSnFqNEZqdlhES1ByMHdBUGdYNmoyOVo4aGZ4Z2NkemRoN1ZjMXJ5ZldZdEo4d3U0UWlTdFk1NzYzYkNlV3MvRXhyaC9zUVpRWTROOS9XcTU2R21tTXJvMm14UHJPdUFLdTdMN3BGTjBNZVk3R1FnRlNLczN6VGZGb1pvQXlZSXZpbHJGS2phTmpwK1p4TXFFcUFRNnRkMXVZZ3VhajZBd1BoNWVvYTA0ZWxxeTdJTmNITmR4SVIzRHAvYURGVGJPMzdCd215dGgyQ0VUZ2hqNlVjcWRldk55VWpranJFdzl1dGIzZnlIR3IxdEhyeFV3MkgxWTVMZHlMQVdDS01wd1QxdlRWN1BtTmxYZ2l4S1hzVVQvVFZReklMOVVJaTBPQWpSZkVjU1o5dkxlNEdKK0MxT1JzK3BZRVNvYll4dG1mSmg0QWdGYUYwbTV3cGF5Y0VEbWJLZDB0bWk2cGFSbGFXeCt0MGhFQ08zaEFSMmU1b2lBNkpaZitqYmVQOFFRcEhXMk0yK25tQ1R5MlhERmZSUDUwbVp2Rkc2cDVWNjZrUlhsT0g1bjRoT1ZHaU9NbWZMQzFudGFLaCttK25Vc2FvZjVkc2VJc2pqYXFYRHpiM0NTMGJBVS9vMDhkMkdlSytmYWhVUmxidnBWNVdQdzhOV3dmeG9DVEhEeDNaRXBRVHJMNWMvcnVxcGhtVEdNMHJoMlpDQy9ibzlBSUFHU2NGOXQzRlVwc1YyaTc3OXU3OUJkcTM3SFUya29DOTBwNll6eHg4ai9xQkh2V2FIOGhaUUttUTV5bDVkL2lYSkxpSFlaaEwraTFUYjNHN2t3WUV5OTFwRVdkaWVjWkc1TDhmWTk3OHpDcjBMT21ydzIrL2k1ZDNyVkNvajZEN1pZMHJQOEpEYWFXdTlhTDhVbGZiVTJOMDA4OUhYVUFoR1daUVNhZkY4MUxjSFVLN1VhTU92USt1Z0t2c29Mdno4Q291aTFKL0R5V2QvYkV4Y1JmWENsVTlWbldmWkpzblFaQWw4diszbnF5ZWpQMmY5Y3MzU3NtV3VRYz0iLCJkYXRha2V5IjoiQVFJQkFIaEFPc2FXMmdaTjA5V050TkdrWWM4cXAxMXhTaFovZHJFRW95MUhrOExYV2dFeXZKTSt4amNoMGF0TDJRa3E0K2FJQUFBQWZqQjhCZ2txaGtpRzl3MEJCd2FnYnpCdEFnRUFNR2dHQ1NxR1NJYjNEUUVIQVRBZUJnbGdoa2dCWlFNRUFTNHdFUVFNYXdjcW15Z0l5c3ZZMTAwMUFnRVFnRHZtNFM5ZW9BL2pid3ZCTnh3cHZoWUNtdVA2aytoL1JiUXJhMmx3L0hkQ1I0OHJMSkRBNUxGZXFwWnlQaGtGdk5Kc0kySEpQTUt0YmRGUGxBPT0iLCJ2ZXJzaW9uIjoiMiIsInR5cGUiOiJEQVRBX0tFWSIsImV4cGlyYXRpb24iOjE2NTEzMzY3NjF9