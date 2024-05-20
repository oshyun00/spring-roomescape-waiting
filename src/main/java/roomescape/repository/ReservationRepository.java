package roomescape.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.domain.Member;
import roomescape.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByDateAndTimeIdAndThemeId(LocalDate date, Long timeId, Long themeId);

    List<Reservation> findAllByThemeIdAndMemberIdAndDateBetween(Long themeId, Long memberId, LocalDate dateFrom,
                                                                LocalDate dateTo);

    boolean existsByTimeId(Long timeId);

    boolean existsByThemeId(Long themeId);

    List<Reservation> findByMember(Member member);
}
