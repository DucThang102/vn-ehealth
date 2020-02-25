package vn.ehealth.emr.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrYhctNhaBa {
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaybatdauchuaNhaba;
    public String tenbacsichuaNhaba;
    public String chandoanNhaba;
}
