package com.tui.proof.ws.controller.resolver;

import com.tui.proof.ws.service.flight.FlightFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDate;

public class FlightFilterArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(FlightFilter.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        FlightFilter filter = new FlightFilter();
        filter.setAirportOrigin(resolveAirportOrig(webRequest));
        filter.setAirportDestination(resolveAirportDest(webRequest));
        filter.setFrom(resolveFrom(webRequest));
        filter.setTo(resolveTo(webRequest));
        filter.setInfants(resolveInfants(webRequest));
        filter.setChildren(resolveInfants(webRequest));
        filter.setChildren(resolveChildren(webRequest));
        filter.setAdults(resolveAdults(webRequest));
        return filter;
    }

    protected String resolveAirportOrig(@NonNull NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return getRequiredParam(webRequest, "airportOrig", String.class);
    }

    protected String resolveAirportDest(@NonNull NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return getRequiredParam(webRequest, "airportDest", String.class);
    }

    private LocalDate resolveFrom(NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return LocalDate.parse(getRequiredParam(webRequest, "from", LocalDate.class));
    }

    private LocalDate resolveTo(NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return LocalDate.parse(getRequiredParam(webRequest, "to", LocalDate.class));
    }

    private short resolveInfants(NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return Short.parseShort(getRequiredParam(webRequest, "infants", Short.class));
    }

    private short resolveChildren(NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return Short.parseShort(getRequiredParam(webRequest, "children", Short.class));
    }

    private short resolveAdults(NativeWebRequest webRequest) throws MissingServletRequestParameterException {
        return Short.parseShort(getRequiredParam(webRequest, "adults", Short.class));
    }

    private <T> String getRequiredParam(NativeWebRequest webRequest, String paramKey, T type) throws MissingServletRequestParameterException {
        String param = webRequest.getParameter(paramKey);

        if (StringUtils.isBlank(param)) {
            throw new MissingServletRequestParameterException(paramKey, type.getClass().getTypeName());
        }

        return param;
    }
}
