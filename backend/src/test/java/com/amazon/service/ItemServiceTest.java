package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@DisplayName("A test case for class ItemService.")
@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    void setup() {
        itemService = new ItemService(itemRepository);
    }

    @DisplayName("Test findAllByItemIds method.")
    @Test
    public void testFindAllByItemIds() {
        List<Integer> itemIds = Arrays.asList(1, 2, 3);
        when(itemRepository.findAllByIdIn(itemIds))
                .thenReturn(Arrays.asList(
                        new Item(1, "item a", 200.3, "brand a", "description a", 3.5, true, "http://google.image.com", 30),
                        new Item(2, "item b", 500.4, "brand b", "description b", 3.3, true, "http://google.image.com", 20)
                        ));
        List<Item> items = itemService.findAllByItemIds(itemIds);
        assertThat(items)
                .isNotEmpty()
                .hasSize(2)
                .extracting(Item::getId, Item::getName, Item::getBrand)
                .containsExactlyInAnyOrder(
                        tuple(1, "item a", "brand a"),
                        tuple(2, "item b", "brand b"));
    }
}
