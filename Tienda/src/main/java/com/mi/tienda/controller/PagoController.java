package com.mi.tienda.controller;



import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mercadopago.exceptions.MPException;

import com.mi.tienda.model.ProductoModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {
    @PostMapping("/create_preference")
	public Preference pago(@RequestBody ProductoModel pM) throws MPException, MPApiException {
		PreferenceClient client = new PreferenceClient();

// Crea un ítem en la preferencia
		List<PreferenceItemRequest> items = new ArrayList<>();
		PreferenceItemRequest item =
				PreferenceItemRequest.builder()
				.id("1234")
				.title(pM.getP_name())
				.description(pM.getDescription())
				.categoryId("home")
				.quantity(pM.getQuantity())
				.currencyId("BRL")
				.unitPrice(new BigDecimal(pM.getPrice()))
				.build();
		items.add(item);
		PreferenceBackUrlsRequest backUrls =

				PreferenceBackUrlsRequest.builder()
						.success("http://localhost:8080/pago/success")
						.pending("http://localhost:8080/pago/pending")
						.failure("http://localhost/pago/failure")
						.build();

    
		List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
		excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("master").build());
		excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("amex").build());

		List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
		excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

		PreferencePaymentMethodsRequest paymentMethods =
				PreferencePaymentMethodsRequest.builder()
						.excludedPaymentMethods(excludedPaymentMethods)
						.excludedPaymentTypes(excludedPaymentTypes)
						.installments(5)
						.build();

		PreferenceRequest request = PreferenceRequest.builder()
				.items(items)
				.autoReturn("approved")
				.paymentMethods(paymentMethods)
				.backUrls(backUrls)
		.build();

		Preference resulset = client.create(request);

 
		return resulset;
	}
    @PostMapping("/notification")
    public ResponseEntity<String> noficaciones(@RequestParam String data_id, @RequestParam String type) {

    	System.out.println("DATA_ID: "+data_id);
        System.out.println("TYPE: "+type);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }
    
    @GetMapping("/pending")
    public ModelAndView pending(@RequestParam String collection_id,
    		@RequestParam String collection_status, 
    		@RequestParam String payment_id, 
    		@RequestParam String status, 
    		@RequestParam String external_reference, 
    		@RequestParam String payment_type, 
    		@RequestParam String merchant_order_id, 
    		@RequestParam String preference_id,
    		@RequestParam String site_id, 
    		@RequestParam String processing_mode,
    		@RequestParam String merchant_account_id) {
    	ModelAndView mav = new ModelAndView("respuesta");
    	mav.addObject("estado", "PENDING");
    	mav.addObject("collection_id", collection_id);
    	mav.addObject("collection_status", collection_status);
    	mav.addObject("payment_id", payment_id);
    	mav.addObject("status", status);
    	mav.addObject("external_reference", external_reference);
    	mav.addObject("payment_type", payment_type);
    	mav.addObject("merchant_order_id", merchant_order_id);
    	mav.addObject("preference_id", preference_id);
    	mav.addObject("site_id", site_id);
    	mav.addObject("processing_mode", processing_mode);
    	mav.addObject("merchant_account_id", merchant_account_id);
    	return mav;
    }
    @GetMapping("/failure")
    public ModelAndView failure(@RequestParam String collection_id,
    		@RequestParam String collection_status, 
    		@RequestParam String payment_id, 
    		@RequestParam String status, 
    		@RequestParam String external_reference, 
    		@RequestParam String payment_type, 
    		@RequestParam String merchant_order_id, 
    		@RequestParam String preference_id,
    		@RequestParam String site_id, 
    		@RequestParam String processing_mode,
    		@RequestParam String merchant_account_id) {
    	ModelAndView mav = new ModelAndView("respuesta");
    	mav.addObject("estado", "FAILURE");
    	mav.addObject("collection_id", collection_id);
    	mav.addObject("collection_status", collection_status);
    	mav.addObject("payment_id", payment_id);
    	mav.addObject("status", status);
    	mav.addObject("external_reference", external_reference);
    	mav.addObject("payment_type", payment_type);
    	mav.addObject("merchant_order_id", merchant_order_id);
    	mav.addObject("preference_id", preference_id);
    	mav.addObject("site_id", site_id);
    	mav.addObject("processing_mode", processing_mode);
    	mav.addObject("merchant_account_id", merchant_account_id);
    	return mav;
    }
    
    @GetMapping("/success")
    public ModelAndView success(@RequestParam String collection_id,
    		@RequestParam String collection_status, 
    		@RequestParam String payment_id, 
    		@RequestParam String status, 
    		@RequestParam String external_reference, 
    		@RequestParam String payment_type, 
    		@RequestParam String merchant_order_id, 
    		@RequestParam String preference_id,
    		@RequestParam String site_id, 
    		@RequestParam String processing_mode,
    		@RequestParam String merchant_account_id) {
    	
    	ModelAndView mav = new ModelAndView("respuesta");
    	mav.addObject("estado", "SUCCESSS");
    	mav.addObject("collection_id", collection_id);
    	mav.addObject("collection_status", collection_status);
    	mav.addObject("payment_id", payment_id);
    	mav.addObject("status", status);
    	mav.addObject("external_reference", external_reference);
    	mav.addObject("payment_type", payment_type);
    	mav.addObject("merchant_order_id", merchant_order_id);
    	mav.addObject("preference_id", preference_id);
    	mav.addObject("site_id", site_id);
    	mav.addObject("processing_mode", processing_mode);
    	mav.addObject("merchant_account_id", merchant_account_id);
    	return mav;
    }
}
