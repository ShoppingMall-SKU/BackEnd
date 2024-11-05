package com.mealKit.backend.service;

import com.mealKit.backend.domain.Product;
import com.mealKit.backend.domain.enums.ProductStatus;
import com.mealKit.backend.dto.ProductResponseDto;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //CRUD
    // Create Product
    public void createProduct(String name, String detail, Integer price, String img, Integer sale, Integer stock,
                              String brand, ProductStatus staus){


        this.productRepository.save(Product
                .builder()
                        .brand(brand)
                        .create_date(LocalDateTime.now())
                        .detail(detail)
                        .price(price)
                        .img(img)
                        .name(name)
                        .sale(sale)
                        .status(staus)
                        .stock(stock)
                .build());
    }

    // Read Product
    public Product getProductById(Integer id){
        return this.productRepository.findById(id).orElseThrow((() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE)));
    }



    public Product getProductByName(String name){
        Optional<Product> product = this.productRepository.findByName(name);
        if(product.isPresent()){
            return product.get();
        }else{
            throw new RuntimeException("Data Not Found");
        }
    }
    public List<ProductResponseDto> getAll(){ // 전체 조회
        return productRepository.findAll().stream().map(ProductResponseDto::toEntity).toList();
    }

    public List<Product> searchByName(String name){ // 검색기능
        return this.productRepository.findByNameContaining(name);
    }
    // brand 별 상품 찾기
    public List<Product> getByBrand(String brand){
        return this.productRepository.findByBrand(brand);
    }

    // Update Product
    // User 추가하여 인증 수정해야함
    public void modifiedProduct(Product product, String name, String img, int price, int stock){
//        product.setName(name);
//        product.setImg(img);
//        product.setPrice(price);
//        product.setStock(modifiedStock(stock));
//        this.productRepository.save(product);
    }

    // 할인율 변경
    public void modifiedSale(Product product, int sale){
//        product.setSale(sale);
//        this.productRepository.save(product);
    }

    // 수량 변경
    public int modifiedStock(int stock){
        if(stock >= 0){
            return stock;
        }else{
            throw new RuntimeException("음수는 불가능 합니다.");
        }
    }
    // Delete Product
    public void deleteProduct(Product product){
        this.productRepository.delete(product);
    }
}
