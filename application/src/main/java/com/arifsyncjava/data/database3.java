package com.arifsyncjava.data;

import com.arifsyncjava.database3.model1.Staff;
import com.arifsyncjava.database3.repository1.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class database3 {

    private final StaffRepository staffRepository;

    @EventListener(ApplicationReadyEvent.class)
    private void  readyEvent () {
        Staff staff = new Staff("1","firstStaff");
        staffRepository.save(staff);
    }



}



