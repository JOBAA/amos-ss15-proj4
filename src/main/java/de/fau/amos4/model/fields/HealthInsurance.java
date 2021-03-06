package de.fau.amos4.model.fields;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import de.fau.amos4.configuration.AppContext;

/**
 * Personalfragebogen 2.0. Revolutionize form data entry for taxation and
 * other purposes.
 * Copyright (C) 2015 Attila Bujaki, Werner Sembach, Jonas Gröger, Oswaldo
 *     Bejarano, Ardhi Sutadi, Nikitha Mohan, Benedikt Rauh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created by Yao Bochao on 29/06/2015.
 */
public enum HealthInsurance {

    _0(0, "EMPLOYEE.healthInsurance._0"),
    _1(1, "EMPLOYEE.healthInsurance._1"),
    _3(3, "EMPLOYEE.healthInsurance._3"),
    _4(4, "EMPLOYEE.healthInsurance._4"),
    _5(5, "EMPLOYEE.healthInsurance._5"),
    _6(6, "EMPLOYEE.healthInsurance._6"),
    _9(9, "EMPLOYEE.healthInsurance._9"),
    ;

    private int value;

    private String text;

    HealthInsurance(int value, String text)
    {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText()
    {
        Locale locale = LocaleContextHolder.getLocale();
        return AppContext.getApplicationContext().getMessage(text, null, locale);
    }

}
