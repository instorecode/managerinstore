package br.com.instore.web.controller;
//
//import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
//import br.com.caelum.vraptor.ioc.Component;
//import br.com.caelum.vraptor.serialization.ProxyInitializer;
//import br.com.caelum.vraptor.serialization.Serializer;
//
//import br.com.caelum.vraptor.serialization.xstream.XStreamJSONSerialization;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class CharsetJSONSerialization extends XStreamJSONSerialization {
//    private HttpServletResponse response;
//    
//    public CharsetJSONSerialization(HttpServletResponse response, TypeNameExtractor extractor, ProxyInitializer initializer, XStreamBuilder builder) {
//        super(response, extractor, initializer, builder);
//        this.response = response;
//    }
//
//    @Override
//    public <T> Serializer from(T object) {
//        this.response.setContentType("application/json; charset=ISO-8859-1");
//        return getSerializer().from(object);
//    }
//    
//}
