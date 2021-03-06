package productsshop.service;

import productsshop.domain.dtos.ProductInRangeDto;
import productsshop.domain.dtos.ProductSeedDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductInRangeDto> productInRange(BigDecimal more, BigDecimal less);


}
