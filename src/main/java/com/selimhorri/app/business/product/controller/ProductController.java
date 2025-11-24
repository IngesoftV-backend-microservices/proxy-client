package com.selimhorri.app.business.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selimhorri.app.business.product.model.ProductDto;
import com.selimhorri.app.business.product.model.response.ProductProductServiceCollectionDtoResponse;
import com.selimhorri.app.business.product.service.ProductClientService;
import com.selimhorri.app.config.feature.FeatureToggleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	
	private final ProductClientService productClientService;
	private final FeatureToggleService featureToggleService;
	
	@GetMapping
	public ResponseEntity<ProductProductServiceCollectionDtoResponse> findAll(
			@RequestParam(required = false) String search) {
		
		ProductProductServiceCollectionDtoResponse response = this.productClientService.findAll().getBody();
		
		if (featureToggleService.isFeatureEnabled("enhanced-search") && search != null && !search.isBlank()
				&& response != null && !CollectionUtils.isEmpty(response.getCollection())) {
			log.info("Applying enhanced search filter with query: {}", search);
			response.getCollection().removeIf(product -> product.getProductTitle() == null
					|| !product.getProductTitle().toLowerCase().contains(search.toLowerCase()));
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> findById(@PathVariable("productId") final String productId) {
		return ResponseEntity.ok(this.productClientService.findById(productId).getBody());
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> save(@RequestBody final ProductDto productDto) {
		return ResponseEntity.ok(this.productClientService.save(productDto).getBody());
	}
	
	@PutMapping
	public ResponseEntity<ProductDto> update(@RequestBody final ProductDto productDto) {
		return ResponseEntity.ok(this.productClientService.update(productDto).getBody());
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> update(@PathVariable("productId") final String productId, 
			@RequestBody final ProductDto productDto) {
		return ResponseEntity.ok(this.productClientService.update(productId, productDto).getBody());
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("productId") final String productId) {
		return ResponseEntity.ok(this.productClientService.deleteById(productId).getBody());
	}
	
	
	
}










