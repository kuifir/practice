package com.kuifir.normal.enums;

/**
 * 枚举类内嵌套枚举
 *
 * @author kuifir
 * @date 2023/5/31 23:53
 */
public enum SecurityCategory {
    /**
     * 库存
     */
    STOCK(Security.Stock.class),
    BOND(Security.Bond.class);

    private Security[] values;

    SecurityCategory(Class<? extends Security> kind) {
        values = kind.getEnumConstants();
    }

    interface Security {
        enum Stock implements Security {
            SHORT, LONG, MARGIN
        }

        enum Bond implements Security {
            MUNICIPAL, JUNK
        }
    }

    public Security randomSelection() {
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            SecurityCategory category = Enums.random(SecurityCategory.class);
            System.out.println(category + ": " + category.randomSelection());
        }
    }
}
