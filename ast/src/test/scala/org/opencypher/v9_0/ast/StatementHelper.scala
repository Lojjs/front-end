/*
 * Copyright © 2002-2020 Neo4j Sweden AB (http://neo4j.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencypher.v9_0.ast

import org.opencypher.v9_0.ast.semantics.Scope
import org.opencypher.v9_0.ast.semantics.SemanticCheckResult
import org.opencypher.v9_0.ast.semantics.SemanticFeature
import org.opencypher.v9_0.ast.semantics.SemanticState
import org.scalatest.Assertions

object StatementHelper extends Assertions {

  implicit class RichStatement(ast: Statement) {
    def semanticState(features: SemanticFeature*): SemanticState =
      ast.semanticCheck(SemanticState.clean.withFeatures(features: _*)) match {
        case SemanticCheckResult(state, errors) =>
          if (errors.isEmpty) {
            state
          } else
            fail(s"Failure during semantic checking of $ast with errors $errors")
      }

    def scope: Scope = semanticState().scopeTree
  }
}
