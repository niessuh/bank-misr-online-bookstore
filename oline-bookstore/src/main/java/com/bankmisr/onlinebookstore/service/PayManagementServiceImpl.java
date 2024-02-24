package com.bankmisr.onlinebookstore.service;

import org.springframework.stereotype.Service;

@Service
public class PayManagementServiceImpl implements PayManagementService{

    @Override
    public boolean pay() {
         //go to the integration between this module and bank gateway
        //send email with payment process detail along with translation Id
        //save payment history
        //check If approved payment then return true else return true;
        return true;
    }
}
