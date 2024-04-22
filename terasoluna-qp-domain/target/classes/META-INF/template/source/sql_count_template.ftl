SELECT count(*) FROM ${tableCode}
<where>
	<if test="conditions != null and conditions.size > 0">
		<foreach item="item" collection="conditions">
			${item.logicCode}
			<if test="item.functionCode != null and item.functionCode != ''">${item.functionCode} (</if>
			${item.columnName}
			<if test="item.functionCode != null and item.functionCode != ''">
				<choose>
					<when test="item.pattern != null and item.pattern != ''">, #{item.pattern})</when>
					<otherwise>)</otherwise>
				</choose>
			</if>
			${item.operator}
			<if test="item.functionCode != null and item.functionCode != ''">${item.functionCode} (</if>
			
			<![CDATA[ #{item.value}]]>

			<if test="item.functionCode != null and item.functionCode != ''">
				<choose>
					<when test="item.pattern != null and item.pattern != ''">, #{item.pattern})</when>
					<otherwise>)</otherwise>
				</choose>
			</if>
		</foreach>
	</if>
</where>	