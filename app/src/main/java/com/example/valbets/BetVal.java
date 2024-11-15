package com.example.valbets;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.1.
 */
@SuppressWarnings("rawtypes")
public class BetVal extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b50335f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610c1b8061005b5f395ff3fe6080604052600436106100a6575f3560e01c80638bea7c37116100635780638bea7c37146101965780638da5cb5b146101c0578063a8c0bc00146101ea578063d3390e8b14610226578063df7b05d414610262578063e5deb28d1461029e576100a6565b80631643c8c4146100aa5780632ddb52d3146100d25780632e98a1e3146100fc5780633abcaed4146101265780635b885551146101625780637e240c9e1461018c575b5f80fd5b3480156100b5575f80fd5b506100d060048036038101906100cb9190610850565b6102a8565b005b3480156100dd575f80fd5b506100e661038a565b6040516100f3919061089d565b60405180910390f35b348015610107575f80fd5b50610110610390565b60405161011d919061089d565b60405180910390f35b348015610131575f80fd5b5061014c600480360381019061014791906108b6565b610396565b6040516101599190610920565b60405180910390f35b34801561016d575f80fd5b506101766103d1565b604051610183919061089d565b60405180910390f35b6101946103d7565b005b3480156101a1575f80fd5b506101aa61052b565b6040516101b7919061089d565b60405180910390f35b3480156101cb575f80fd5b506101d4610531565b6040516101e19190610920565b60405180910390f35b3480156101f5575f80fd5b50610210600480360381019061020b9190610963565b610554565b60405161021d919061089d565b60405180910390f35b348015610231575f80fd5b5061024c600480360381019061024791906108b6565b610569565b6040516102599190610920565b60405180910390f35b34801561026d575f80fd5b5061028860048036038101906102839190610963565b6105a4565b604051610295919061089d565b60405180910390f35b6102a66105b9565b005b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610335576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161032c90610a0e565b60405180910390fd5b81600181905550806002819055506002546001541115610366576103616005600360075460085461070d565b610386565b6001546002541115610385576103846006600460085460075461070d565b5b5b5050565b60085481565b60075481565b600581815481106103a5575f80fd5b905f5260205f20015f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60015481565b5f3411610419576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161041090610a9c565b60405180910390fd5b5f60035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054036104be57600533908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b3460035f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f82825461050a9190610ae7565b925050819055503460075f8282546105229190610ae7565b92505081905550565b60025481565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6004602052805f5260405f205f915090505481565b60068181548110610578575f80fd5b905f5260205f20015f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6003602052805f5260405f205f915090505481565b5f34116105fb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105f290610a9c565b60405180910390fd5b5f60045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2054036106a057600633908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b3460045f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f8282546106ec9190610ae7565b925050819055503460085f8282546107049190610ae7565b92505081905550565b5f5b8480549050811015610812575f85828154811061072f5761072e610b1a565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505f855f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205490505f8585836107a79190610b47565b6107b19190610bb5565b826107bc9190610ae7565b90508273ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f19350505050158015610801573d5f803e3d5ffd5b50505050808060010191505061070f565b5050505050565b5f80fd5b5f819050919050565b61082f8161081d565b8114610839575f80fd5b50565b5f8135905061084a81610826565b92915050565b5f806040838503121561086657610865610819565b5b5f6108738582860161083c565b92505060206108848582860161083c565b9150509250929050565b6108978161081d565b82525050565b5f6020820190506108b05f83018461088e565b92915050565b5f602082840312156108cb576108ca610819565b5b5f6108d88482850161083c565b91505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61090a826108e1565b9050919050565b61091a81610900565b82525050565b5f6020820190506109335f830184610911565b92915050565b61094281610900565b811461094c575f80fd5b50565b5f8135905061095d81610939565b92915050565b5f6020828403121561097857610977610819565b5b5f6109858482850161094f565b91505092915050565b5f82825260208201905092915050565b7f4f6e6c79206f776e65722063616e206465636c617265207468652077696e6e655f8201527f7200000000000000000000000000000000000000000000000000000000000000602082015250565b5f6109f860218361098e565b9150610a038261099e565b604082019050919050565b5f6020820190508181035f830152610a25816109ec565b9050919050565b7f42657420616d6f756e74206d7573742062652067726561746572207468616e205f8201527f7a65726f00000000000000000000000000000000000000000000000000000000602082015250565b5f610a8660248361098e565b9150610a9182610a2c565b604082019050919050565b5f6020820190508181035f830152610ab381610a7a565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610af18261081d565b9150610afc8361081d565b9250828201905080821115610b1457610b13610aba565b5b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f610b518261081d565b9150610b5c8361081d565b9250828202610b6a8161081d565b91508282048414831517610b8157610b80610aba565b5b5092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601260045260245ffd5b5f610bbf8261081d565b9150610bca8361081d565b925082610bda57610bd9610b88565b5b82820490509291505056fea264697066735822122038cb7c8a8813b78f46c59acb01016c6031a0dccfaf2430267dc4c64b24d0126764736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DECLAREWINNER = "declareWinner";

    public static final String FUNC_PLACEBETONTEAMA = "placeBetOnTeamA";

    public static final String FUNC_PLACEBETONTEAMB = "placeBetOnTeamB";

    public static final String FUNC_BETSONTEAMA = "betsOnTeamA";

    public static final String FUNC_BETSONTEAMB = "betsOnTeamB";

    public static final String FUNC_BETTORSONTEAMA = "bettorsOnTeamA";

    public static final String FUNC_BETTORSONTEAMB = "bettorsOnTeamB";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TEAMASCORE = "teamAScore";

    public static final String FUNC_TEAMBSCORE = "teamBScore";

    public static final String FUNC_TOTALBETONTEAMA = "totalBetOnTeamA";

    public static final String FUNC_TOTALBETONTEAMB = "totalBetOnTeamB";

    @Deprecated
    protected BetVal(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BetVal(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BetVal(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BetVal(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> declareWinner(BigInteger _teamAScore,
            BigInteger _teamBScore) {
        final Function function = new Function(
                FUNC_DECLAREWINNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_teamAScore), 
                new org.web3j.abi.datatypes.generated.Uint256(_teamBScore)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> placeBetOnTeamA(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PLACEBETONTEAMA, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> placeBetOnTeamB(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PLACEBETONTEAMB, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> betsOnTeamA(String param0) {
        final Function function = new Function(FUNC_BETSONTEAMA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> betsOnTeamB(String param0) {
        final Function function = new Function(FUNC_BETSONTEAMB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> bettorsOnTeamA(BigInteger param0) {
        final Function function = new Function(FUNC_BETTORSONTEAMA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> bettorsOnTeamB(BigInteger param0) {
        final Function function = new Function(FUNC_BETTORSONTEAMB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> teamAScore() {
        final Function function = new Function(FUNC_TEAMASCORE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> teamBScore() {
        final Function function = new Function(FUNC_TEAMBSCORE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalBetOnTeamA() {
        final Function function = new Function(FUNC_TOTALBETONTEAMA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalBetOnTeamB() {
        final Function function = new Function(FUNC_TOTALBETONTEAMB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static BetVal load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new BetVal(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BetVal load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BetVal(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BetVal load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new BetVal(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BetVal load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BetVal(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BetVal> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BetVal.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<BetVal> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BetVal.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BetVal> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BetVal.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BetVal> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BetVal.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
