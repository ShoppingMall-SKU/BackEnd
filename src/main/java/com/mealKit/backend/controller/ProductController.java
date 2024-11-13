package com.mealKit.backend.controller;

import com.mealKit.backend.dto.ProductResponseDto;
import com.mealKit.backend.dto.ProductPostDto;
import com.mealKit.backend.exception.ResponseDto;
import com.mealKit.backend.service.ProductService;
import com.mealKit.backend.domain.Product;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list/{page}") // 전체 조회 test완
    public ResponseDto<?> getProductList(@PathVariable("page") Integer page){
        return ResponseDto.ok(this.productService.getAll(page));
    }

    @GetMapping("/search/{page}") // 검색기능
    public ResponseEntity<?> getProductByName(@PathVariable("page") Integer page,
                                              @RequestParam("query") String query) {
        log.info(query);
        return ResponseEntity.ok(this.productService.searchProduct(page, query));
    }

    @GetMapping("/list/brands/{brand}") // brand별 검색 기능 test완
    public ResponseEntity<List<Product>> getProductByBrand(@PathVariable String brand){
        List<Product> products = productService.getByBrand(brand);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/detail/{id}") // 상품 상세 조회 test완
    public ResponseDto<ProductResponseDto> getProductDetail(@PathVariable Integer id){
        return ResponseDto.ok(ProductResponseDto.toEntity(this.productService.getProductById(id)));
    }

//    @PostMapping("/create-product") // test완
//    public ResponseEntity<List<Product>> postProduct(@RequestBody ProductPostDto productPostDto){
//        productService.createProduct(productPostDto.getName(), productPostDto.getDetail(),productPostDto.getPrice(),
//                productPostDto.getImg(),productPostDto.getSale(),productPostDto.getStock(),
//                productPostDto.getBrand(),productPostDto.getStatus());
//        return ResponseEntity.ok(productService.getAll());
//    }
//
//    @PatchMapping("/detail/{id}")
//    public ResponseEntity<List<Product>> pathProduct(@PathVariable int id, @RequestBody ProductResponseDto productResponseDto){
//        Product product = productService.getProductById(id);
//        productService.modifiedProduct(product, productResponseDto.getName(), productResponseDto.getImg(),
//                productResponseDto.getPrice(), productResponseDto.getStock());
//
//        return ResponseEntity.ok((productService.getAll()));
//    }
//
//    @PatchMapping("/detail/sale/{id}")
//    public ResponseEntity<List<Product>> pathProductSale(@PathVariable int id, @RequestBody ProductResponseDto productResponseDto){
//        Product product = productService.getProductById(id);
//        productService.modifiedSale(product, productResponseDto.getSale());
//
//        return ResponseEntity.ok((productService.getAll()));
//    }
//
//    @DeleteMapping("/detail/{id}")
//    public ResponseEntity<List<Product>> deleteProduct(@PathVariable int id){
//        Product product = productService.getProductById(id);
//        productService.deleteProduct(product);
//
//        return ResponseEntity.ok(productService.getAll());
//    }
}
