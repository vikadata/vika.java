/*
 * Copyright (C) 2021 vikadata
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package cn.vika.core.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.vika.core.utils.AssertUtil;

/**
 * @author Shawn Deng
 * @date 2020-11-17 18:12:09
 */
public abstract class GenericTypeReference<T> {

    private final Type type;

    protected GenericTypeReference() {
        Class<?> genericTypeReferenceSubclass = findGenericTypeReferenceSubclass(getClass());
        Type type = genericTypeReferenceSubclass.getGenericSuperclass();
        AssertUtil.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        AssertUtil.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
        this.type = actualTypeArguments[0];
    }

    private GenericTypeReference(Type type) {
        this.type = type;
    }


    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        return (this == other || (other instanceof GenericTypeReference &&
                this.type.equals(((GenericTypeReference<?>) other).type)));
    }

    @Override
    public int hashCode() {
        return this.type.hashCode();
    }

    @Override
    public String toString() {
        return "GenericTypeReference<" + this.type + ">";
    }


    /**
     * Build a {@code GenericTypeReference} wrapping the given type.
     *
     * @param type a generic type (possibly obtained via reflection,
     *             e.g. from {@link java.lang.reflect.Method#getGenericReturnType()})
     * @return a corresponding reference which may be passed into
     */
    public static <T> GenericTypeReference<T> forType(Type type) {
        return new GenericTypeReference<T>(type) {
        };
    }

    private static Class<?> findGenericTypeReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        if (Object.class == parent) {
            throw new IllegalStateException("Expected GenericTypeReference superclass");
        }
        else if (GenericTypeReference.class == parent) {
            return child;
        }
        else {
            return findGenericTypeReferenceSubclass(parent);
        }
    }
}
