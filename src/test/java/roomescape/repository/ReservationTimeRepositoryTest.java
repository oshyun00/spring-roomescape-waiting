package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationTimeRequest;

@DataJpaTest
class ReservationTimeRepositoryTest {
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    private long getItemSize() {
        return reservationTimeRepository.findAll().size();
    }

    @DisplayName("Db에 등록된 시간 목록을 조회할 수 있다.")
    @Test
    void given_when_findAll_then_ReturnReservationTimes() {
        //given, when, then
        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(4);
    }

    @DisplayName("Db에 시간 정보를 저장한다.")
    @Test
    void given_reservationTimeRequest_when_create_then_returnCreatedTimeId() {
        //given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.parse("10:11"));
        ReservationTime expected = reservationTimeRequest.toEntity();
        //when
        ReservationTime savedReservationTime = reservationTimeRepository.save(expected);
        //then
        assertThat(savedReservationTime).isEqualTo(expected);
    }

    @DisplayName("시간 Id로 Db에서 시간 정보를 삭제한다.")
    @Test
    void given_when_delete_then_deletedFromDb() {
        //given
        long initialSize = getItemSize();
        //when
        reservationTimeRepository.deleteById(4L);
        long afterSize = getItemSize();
        //then
        assertThat(afterSize).isEqualTo(initialSize - 1);
    }

    @DisplayName("시간이 Db에 이미 등록되어 있는지 확인할 수 있다.")
    @Test
    void given_when_isExist_then_returnExistResult() {
        //given, when, then
        assertThat(reservationTimeRepository.existsByStartAt(LocalTime.parse("10:00"))).isTrue();
    }

    @DisplayName("Id를 통해 시간 정보를 반환할 수 있다.")
    @Test
    void given_when_findById_then_returnOptionalReservationTime() {
        //given, when, then
        assertThat(reservationTimeRepository.findById(1L).get().getId()).isEqualTo(1);
    }

    @DisplayName("특정 날짜와 테마Id를 통해 예약된 시간들을 반환할 수 있다.")
    @Test
    void given_when_exist_then_returnReservedReservationTimes() {
        //given, when, then
        assertThat(reservationTimeRepository.findByDateAndThemeId(LocalDate.parse("2024-05-01"), 2L).size()).isEqualTo(
                2);
    }
}
