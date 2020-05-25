package org.zerock;


import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.domain.Member;
import org.zerock.domain.Profile;
import org.zerock.persistence.MemberRepository;
import org.zerock.persistence.ProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log
@Commit // 테스트 결과를 DB에 commit
public class ProfileTests {

    @Autowired
    MemberRepository m_repo;

    @Autowired
    ProfileRepository p_repo;

    @Test
    public void testInsertMembers() {
        IntStream.range(1, 101).forEach(i -> { // 1 ~ 100
            Member member = new Member();
            member.setUid("user"+i);
            member.setUpw("pw"+i);
            member.setUname("사용자"+i);

            m_repo.save(member);
        });
    }

    @Test
    public void testInsertProfile() {
        Member member = new Member();

        member.setUid("user1");

        IntStream.range(1, 5).forEach(i -> {

            Profile profile1 = new Profile();
            profile1.setFname("face"+i+".jpg");

            if (i == 1)
                profile1.setCurrent(true);

            profile1.setMember(member);

            p_repo.save(profile1);

        });
    }

    @Test
    public void testFetchJoin1() {
        List<Object[]> result = m_repo.getMemberWithProfileCount("user1");

        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testFetchJoin2() {
        List<Object[]> result = m_repo.getMemberWithProfile("user1");

        result.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }
}
