package writer;

import parser.Products;

public interface Writer {
    void makeFile(Products products, String filename);
}
