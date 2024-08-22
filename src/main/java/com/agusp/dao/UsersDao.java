
package com.agusp.dao;

import com.agusp.base.BaseQuery;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.agusp.model.Users;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.print.DocFlavor;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsersDao extends BaseQuery{
    private ModelMapper modelMapper;
    
    public long checkExiting(String id, Class<?> module) {
        try {
            String qry = "SELECT COUNT(*) FROM " + getTableName(module) + " WHERE id = '" + id + "' ";
            long countData = countResult(qry);
            return countData;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public Object profile(JSONObject obj, Class<?> module) {
        try {
            String qry = " "
                    + " SELECT email,first_name,lastst_name,profile_image "
                    + " FROM " + getTableName(module) + " "
                    + " WHERE "
                    + "  code = '" + obj.get("code") + "' ";
            return singleResult(qry);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
