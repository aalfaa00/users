package com.zeromax.users.repository;

import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.entity.user.UserType;
import com.zeromax.users.exeptions.CustomGeneralException;
import com.zeromax.users.maps.UserMapper;
import com.zeromax.users.models.EmployeeProfileResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomUsersFilterImpl implements CustomUsersFilter{

    private final MongoTemplate mongoTemplate;
    private final UserMapper userMapper;

    @Autowired
    public CustomUsersFilterImpl(MongoTemplate mongoTemplate, UserMapper userMapper) {
        this.mongoTemplate = mongoTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public Page<EmployeeProfileResponseModel> getFilteredAndOrderedEmployees(Integer page, Integer size, String companyId, UserType userType,
                                                                             String keyword, String sortBy) {
        Sort sort = createSort(sortBy);
        Pageable pageable =
                sort != null ? PageRequest.of(page, size, sort) : PageRequest.of(page, size);

        try {
            Query query = new Query();
            List<Criteria> criteria = createCriteria(userType.toString(), keyword);
            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria));
            }

            Query newQuery = Query.of(query).with(pageable);
            var listOfEmployees = PageableExecutionUtils.getPage(
                    mongoTemplate.find(newQuery, UserEntity.class, "users"),
                    pageable,
                    () -> mongoTemplate.count(query, UserEntity.class));
            var companyEmployees = listOfEmployees.stream().
                    map(userMapper::mapUserEntityToEmployeeProfileResponseModel);
            var result = companyEmployees.filter(employee ->
                    employee.getCompanyId().equals(companyId)).collect(Collectors.toList());
            return new PageImpl<>(result, pageable, result.size());
        }catch(Exception ex){
            throw new CustomGeneralException("Could not query users for given filter options", "0000");
        }
    }

    private List<Criteria> createCriteria(String userType, String keyword){
        List<Criteria> criteria = new ArrayList<>();
        if (userType != null && !userType.isBlank()) {
            criteria.add(Criteria.where("userType").regex(userType, "i"));
        }
        if (keyword != null && !keyword.isBlank()) {
            criteria.add(Criteria.where("firsName").regex(keyword, "i"));
            criteria.add(Criteria.where("lastName").regex(keyword, "i"));
        }
        return criteria;
    }

    private Sort createSort(String order) {
        Sort sort = null;
        if (order != null) {
            String[] arr = order.split("_");
            if (arr.length == 2) {
                var direction =
                        arr[1].toLowerCase(Locale.ROOT).equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
                sort = Sort.by(new Sort.Order(direction, arr[0]));
            }
        }
        return sort;
    }
}
