package com.supermercado_spring.model.enums;

public enum EnumCategoria {

    ALMACEN("Almacen"),
    BEBIDAS("Bebidas"),
    GASEOSAS("Gaseosas"),
    AGUAS("Aguas"),
    JUGOS("Jugos"),
    VINOS("Vinos"),
    CERVEZAS("Cervezas"),
    LACTEOS("Lacteos"),
    QUESOS("Quesos"),
    FIAMBRES("Fimbres"),
    CARNES("Carnes"),
    POLLO("Pollo"),
    PESCADOS("Pescados"),
    FRUTAS("Frutas"),
    VERDURAS("Verduras"),
    PANADERIA("Panadoria"),
    PASTAS("Pastas"),
    CONGELADOS("Congelados"),
    SNACKS("Snacks"),
    GOLOSINAS("Golosinas"),
    DESAYUNO_Y_MERIENDA("Desayuno y Merienda"),
    LIMPIEZA("Limpieza"),
    HIGIENE_PERSONAL("Higiene Personal"),
    PERFUMERIA("Perfumeria"),
    BEBES("Bebes"),
    MASCOTAS("Mascotas"),
    BAZAR("Bazar"),
    LIBRERIA("Libreria"),
    ELECTRODOMESTICOS("Electrodomestico");

    private String descripcion;

    private EnumCategoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescription() {
        return descripcion;
    }
}
