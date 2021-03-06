/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.network;

import c4.conarm.integrations.tinkertoolleveling.ConfigSyncLevelingPacket;
import slimeknights.tconstruct.common.TinkerNetwork;

public class ConstructsNetwork {

    public static void init() {
        TinkerNetwork.instance.registerPacket(ArmorStationSelectionPacket.class);
        TinkerNetwork.instance.registerPacket(ArmorStationTextPacket.class);
        TinkerNetwork.instance.registerPacketServer(AccessoryTogglePacket.class);
        TinkerNetwork.instance.registerPacketClient(SetStepHeightPacket.class);
        TinkerNetwork.instance.registerPacketClient(ConfigSyncLevelingPacket.class);
        TinkerNetwork.instance.registerPacketServer(ArmorBouncedPacket.class);
    }
}
