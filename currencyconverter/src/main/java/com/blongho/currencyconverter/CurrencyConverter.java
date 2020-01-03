package com.blongho.currencyconverter;

import android.widget.TextView;

/**
 * The type Currency converter.
 */
public class CurrencyConverter {

  private TextView updateView;

  private Converter converter = null;
  private CurrencyCode from;
  private CurrencyCode to;

  /**
   * Instantiates a new Currency converter.
   */
  public CurrencyConverter() {
    from = to = null;
  }

  /**
   * From currency converter.
   *
   * @param currencyCode the currency code
   * @return the currency converter
   */
  public CurrencyConverter from(final CurrencyCode currencyCode) {
    this.from = currencyCode;
    return this;
  }

  /**
   * To currency converter.
   *
   * @param currencyCode the currency code
   * @return the currency converter
   */
  public CurrencyConverter to(final CurrencyCode currencyCode) {
    this.to = currencyCode;
    return this;
  }

  /**
   * Build converter.
   *
   * @return the converter
   */
  public Converter build() {
    converter = new Converter(from, to);
    converter.setTextView(updateView);
    return converter;
  }

  public CurrencyConverter putResultAt(TextView view) {
    this.updateView = view;
    return this;
  }
}
