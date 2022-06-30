package kz.example.repository;


import kz.example.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m.message from Message m where m.user.id = :userId order by m.id desc")
    List<String> findMessagesByUserId(@Param("userId") long userId,
                                      Pageable pageable);

}
