package com.conte.hackothumun.service;

import com.conte.hackothumun.entity.Billet;
import com.conte.hackothumun.repository.BilletRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class BilletServiceImpl implements ServiceCRUD<Billet> {
    private BilletRepo billetRepo;
    private static final Logger logger = LoggerFactory.getLogger(BilletServiceImpl.class);

    @Override
    public Billet select(Long id) {
        return billetRepo.findById(id).orElse(null);
    }

    @Override
    public Billet insert(Billet value) {
        System.out.println("***********BilletServiceImpl insert******************");
        return billetRepo.save(value);
    }

    @Override
    public Billet update(Billet value) {
        return billetRepo.save(value);
    }

    @Override
    public void delete(Long value) {
        Billet billet = billetRepo.findById(value).orElse(null);
        if(billet == null) throw new RuntimeException("This billet don't exists");
        billetRepo.deleteById(value);
    }
}
