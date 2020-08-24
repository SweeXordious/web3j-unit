/*
 * Copyright 2020 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.web3j.greeter.Greeter
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger

@EVMTest(type = NodeType.EMBEDDED, genesis = "file:src/test/resources/embedded/genesis.json")
class EmbeddedGenesisTest {
    @Test
    fun greeterDeploys(
        web3j: Web3j,
        transactionManager: TransactionManager,
        gasProvider: ContractGasProvider
    ) {
        val greeter = Greeter.deploy(web3j, transactionManager, gasProvider, "Hello EVM").send()
        val greeting = greeter.greet().send()
        Assertions.assertEquals("Hello EVM", greeting)
    }

    @Test
    fun genesisLoads(
        web3j: Web3j
    ) {
        val expectedAccountBalance = BigInteger.valueOf(16)
        val actualAccountBalance = web3j.ethGetBalance("fe3b557e8fb62b89f4916b721be55ceb828dbd74", DefaultBlockParameter.valueOf(BigInteger.ONE)).send()

        Assertions.assertEquals(expectedAccountBalance, actualAccountBalance.balance)
    }
}
