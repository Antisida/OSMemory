/**************************************************************************
 OSMemory library for OSM data processing.

 Copyright (C) 2014 Aleś Bułojčyk <alex73mail@gmail.com>

 This is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This software is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **************************************************************************/

package org.alex73.osmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class for store some frequently used strings. Each string has own short id, that used in object instead
 * direct string usage.
 * 
 * That allow to minimize storage memory and use short type instead string.
 *
 * Класс для хранения некоторых часто используемых строк.
 * Каждая строка имеет собственный короткий идентификатор, который используется в object прямое использование строки.
 *
 */
public class StringPack {
    final protected Map<String, Short> tagCodes = new HashMap<>();       // две мапы в одной ключ - строка, в другой ключ - код строки
    final protected Map<Short, String> tagNames = new HashMap<>();         // чтобы можно было искать и по строки и по ключу

    /**
     * Возвращает код, соответствующий строки по содержанию строки
     * причем, Если строки нет в мапе, то она добавляется
     */
    public synchronized short getTagCode(String tagName) {
        Short v = tagCodes.get(tagName);     // v - код строки соответстующий имени tagName
        short result;
        if (v == null) {    // если строка не найдена, то
            result = (short) tagCodes.size();   // result равен длине мапы
            if (result >= Short.MAX_VALUE) {
                throw new RuntimeException("Too many tag keys: more than " + Short.MAX_VALUE);
            }
            tagCodes.put(tagName, result);   // помещаем строку, которую искали в мапу
            tagNames.put(result, tagName);
        } else {
            result = v;
        }
        return result;
    }

    /**
     * Возвращает строку, по коду
    */
    public String getTagName(short tagKey) {
        return tagNames.get(tagKey);
    }

    /**
     * Возвращает все строки
     */
    public Set<String> getTagNames() {
        return tagCodes.keySet();
    }
}
