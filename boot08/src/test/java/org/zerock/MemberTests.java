package org.zerock;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.Member;
import org.zerock.domain.MemberRole;
import org.zerock.persistence.MemberRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit
public class MemberTests {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void testInsert() {
        IntStream.range(0, 100).forEach(i -> {
            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" + i);
            member.setUname("사용자" + i);

            MemberRole role = new MemberRole();
            if (i <= 80) {
                role.setRoleName("BASIC");
            } else if (i <= 90) {
                role.setRoleName("MANAGER");
            } else {
                role.setRoleName("ADMIN");
            }
            member.setRoles(Arrays.asList(role));

            repo.save(member);

        });
    }

    @Test
    public void testRead() {
        Optional<Member> result = repo.findById("user85");
        result.ifPresent(member -> {
            log.info("member: " + member);
        });
    }

    @Test
    public void updateOldPassword() {

        List<String> ids = new ArrayList<>();

        IntStream.range(0, 100).forEach(i -> {
            ids.add("user" + i);
        });

        repo.findAllById(ids).forEach(member -> {
            member.setUpw(encoder.encode(member.getUpw()));
            repo.save(member);
        });

    }

}
