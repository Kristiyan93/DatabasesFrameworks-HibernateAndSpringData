package productsshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsshop.domain.dtos.ProductInRangeDto;
import productsshop.domain.dtos.ProductSeedDto;
import productsshop.domain.entities.Category;
import productsshop.domain.entities.Product;
import productsshop.domain.entities.User;
import productsshop.repository.CategoryRepository;
import productsshop.repository.ProductRepository;
import productsshop.repository.UserRepository;
import productsshop.util.ValidatorUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository
            , CategoryRepository categoryRepository
            , UserRepository userRepository
            , ValidatorUtil validatorUtil
            , ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            if (!this.validatorUtil.isValid(productSeedDto)) {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Product entity = this.modelMapper.map(productSeedDto, Product.class);
            entity.setSeller(this.getRandomUser());

            Random random = new Random();

            if (random.nextInt() % 13 != 0) {
                entity.setBuyer(this.getRandomUser());
            }

            entity.setCategories(this.getRandomCategories());

            this.productRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<ProductInRangeDto> productInRange(BigDecimal more, BigDecimal less) {
        List<Product> productsEntities = this.productRepository.findAllByPriceBetweenAndBuyerOrderByPrice(more, less, null);

        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();

        for (Product entity : productsEntities) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(entity, ProductInRangeDto.class);
            productInRangeDto.setSeller(String.format("%s %s", entity.getSeller().getFirstName(), entity.getSeller().getLastName() ));

            productInRangeDtos.add(productInRangeDto);
        }

        return productInRangeDtos;
    }

    private User getRandomUser() {
        Random random = new Random();

        return this.userRepository.getOne(random.nextInt((int)this.userRepository.count() - 1) + 1);
    }

    private List<Category> getRandomCategories() {
        Random random = new Random();

        List<Category> categories = new ArrayList<>();

        Integer counter = random.nextInt((int) this.categoryRepository.count() - 1) + 1;

        for (int i = 0; i < counter; i++) {
            categories.add(this.categoryRepository.getOne(random.nextInt((int) this.categoryRepository.count() - 1) + 1));
        }

        return categories;
    }
}
