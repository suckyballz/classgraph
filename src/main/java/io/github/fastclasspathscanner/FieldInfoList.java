/*
 * This file is part of FastClasspathScanner.
 *
 * Author: Luke Hutchison
 *
 * Hosted at: https://github.com/lukehutch/fast-classpath-scanner
 *
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Luke Hutchison
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.fastclasspathscanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** A list of {@link FieldInfo} objects. */
public class FieldInfoList extends ArrayList<FieldInfo> {

    public FieldInfoList() {
        super();
    }

    public FieldInfoList(final int sizeHint) {
        super(sizeHint);
    }

    public FieldInfoList(final Collection<FieldInfo> fieldInfoCollection) {
        super(fieldInfoCollection);
    }

    static final FieldInfoList EMPTY_LIST = new FieldInfoList() {
        @Override
        public boolean add(final FieldInfo e) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public void add(final int index, final FieldInfo element) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public boolean remove(final Object o) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public FieldInfo remove(final int index) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public boolean addAll(final Collection<? extends FieldInfo> c) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public boolean addAll(final int index, final Collection<? extends FieldInfo> c) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public boolean removeAll(final Collection<?> c) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public boolean retainAll(final Collection<?> c) {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public void clear() {
            throw new IllegalArgumentException("List is immutable");
        }

        @Override
        public FieldInfo set(final int index, final FieldInfo element) {
            throw new IllegalArgumentException("List is immutable");
        }
    };

    // -------------------------------------------------------------------------------------------------------------

    /** Get the names of all fields in this list. */
    public List<String> getNames() {
        if (this.isEmpty()) {
            return Collections.<String> emptyList();
        } else {
            final List<String> fieldNames = new ArrayList<>(this.size());
            for (final FieldInfo fi : this) {
                fieldNames.add(fi.getName());
            }
            return fieldNames;
        }
    }

    /**
     * Get the string representations of all fields in this list (with annotations, modifiers, etc.), by calling
     * {@link FieldInfo#toString()} on each item in the list.
     */
    public List<String> getAsStrings() {
        if (this.isEmpty()) {
            return Collections.<String> emptyList();
        } else {
            final List<String> toStringVals = new ArrayList<>(this.size());
            for (final FieldInfo fi : this) {
                toStringVals.add(fi.toString());
            }
            return toStringVals;
        }
    }

    // -------------------------------------------------------------------------------------------------------------

    /** Return true if this list contains a field with the given name. */
    public boolean containsName(final String fieldName) {
        for (final FieldInfo fi : this) {
            if (fi.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /** Return the {@link FieldInfo} object in the list with the given name, or null if not found. */
    public FieldInfo get(final String fieldName) {
        for (final FieldInfo fi : this) {
            if (fi.getName().equals(fieldName)) {
                return fi;
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------

    /**
     * Filter an {@link FieldInfoList} using a predicate mapping an {@link FieldInfo} object to a boolean, producing
     * another {@link FieldInfoList} for all items in the list for which the predicate is true.
     */
    @FunctionalInterface
    public interface FieldInfoFilter {
        /**
         * Whether or not to allow an {@link FieldInfo} list item through the filter.
         *
         * @param fieldInfo
         *            The {@link FieldInfo} item to filter.
         * @return Whether or not to allow the item through the filter. If true, the item is copied to the output
         *         list; if false, it is excluded.
         */
        public boolean accept(FieldInfo fieldInfo);
    }

    /**
     * Find the subset of the {@link FieldInfo} objects in this list for which the given filter predicate is true.
     *
     * @param filter
     *            The {@link FieldInfoFilter} to apply.
     * @return The subset of the {@link FieldInfo} objects in this list for which the given filter predicate is
     *         true.
     */
    public FieldInfoList filter(final FieldInfoFilter filter) {
        final FieldInfoList fieldInfoFiltered = new FieldInfoList();
        for (final FieldInfo resource : this) {
            if (filter.accept(resource)) {
                fieldInfoFiltered.add(resource);
            }
        }
        return fieldInfoFiltered;
    }
}