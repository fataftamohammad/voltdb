/* This file is part of VoltDB.
 * Copyright (C) 2008-2018 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.voltdb.calciteadapter.rel.physical;

import com.google.common.base.Preconditions;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.plan.volcano.RelSubset;
import org.apache.calcite.rel.RelNode;
import org.voltdb.plannodes.AbstractPlanNode;

import java.util.Objects;

public interface VoltDBPRel extends RelNode {
    final static Convention VOLTDB_PHYSICAL = new Convention.Impl("VOLTDB_PHYSICAL", VoltDBPRel.class) {
        public boolean canConvertConvention(Convention toConvention) {
            return true;
        }

        public boolean useAbstractConvertersForConversion(RelTraitSet fromTraits,
                                                          RelTraitSet toTraits) {
            return true;
        }

    };

    /**
     * Convert VoltDBPRel and its descendant(s) to a AbstractPlanNode tree
     * This is the key piece that bridges between Calcite planner and VoltDB planner.
     * TODO: implement the method in future
     * @return AbstractPlanNode
     */
    default AbstractPlanNode toPlanNode() {
        return null;
    }

    /**
     * Return number of concurrent processes that this VoltDBPRel will be executed in.
     * If this rel/plan node belongs to a coordinator then its split count is 1
     * For a fragment rel/node the split count = a number of hosts * number of sites per host
     *
     * @return Split count
     */
    int getSplitCount();

    /**
     * Return a child VoltDBRel node in a specified position
     *
     * @param node         Parent Node
     * @param childOrdinal Child position
     * @return VoltDBRel
     */
    default VoltDBPRel getInputNode(RelNode node, int childOrdinal) {
        RelNode inputNode = node.getInput(childOrdinal);
        if (inputNode != null) {
            if (inputNode instanceof RelSubset) {
                inputNode = ((RelSubset) inputNode).getBest();
                Objects.requireNonNull(inputNode);

            }
            Preconditions.checkArgument(inputNode instanceof VoltDBPRel);
        }
        return (VoltDBPRel) inputNode;
    }

    /**
     * Convert a child VoltDBRel node in a specified position to an AbstractPlanNode
     *
     * @param node
     * @param childOrdinal
     * @return AbstractPlanNode
     */
    default AbstractPlanNode inputRelNodeToPlanNode(RelNode node, int childOrdinal) {
        VoltDBPRel inputNode = getInputNode(node, childOrdinal);
        Objects.requireNonNull(inputNode);
        return inputNode.toPlanNode();
    }
}
